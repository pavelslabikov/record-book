package ru.example.recordbookbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.CryptoPro.CAdES.CAdESSignature;
import ru.example.recordbookbackend.dto.AggregationStatusDto;
import ru.example.recordbookbackend.dto.CertificateInfoDto;
import ru.example.recordbookbackend.dto.RecordBooksAggregationDto;
import ru.example.recordbookbackend.dto.SignatureInfoDto;
import ru.example.recordbookbackend.dto.controller.AggregationWithStatusDto;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.dto.mapper.*;
import ru.example.recordbookbackend.entity.*;
import ru.example.recordbookbackend.repository.*;
import ru.example.recordbookbackend.service.RecordBookSerializer;
import ru.example.recordbookbackend.service.SignatureValidator;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "aggregation-controller", description = "Контроллер управления агрегациями зачётных книг")
@RequiredArgsConstructor
public class RecordBookController {

    private final SheetRepository sheetRepository;

    private final GradeRepository gradeRepository;


    private final SheetMapper sheetMapper;

    private final GradeMapper gradeMapper;



    private final RecordBooksAggregationMapper aggregationMapper;

    private final RecordBooksAggregationRepository aggregationRepository;

    private final SignatureValidator signatureValidator;


    private final RecordBookSerializer recordBookSerializer;

    private final SignatureInfoRepository signatureInfoRepository;

    private final CertificateInfoRepository certificateInfoRepository;
    private final AggregationStatusRepository aggregationStatusRepository;

    private final AggregationStatusMapper aggregationStatusMapper;

    private final CertificateInfoMapper certificateInfoMapper;

    private final SignatureInfoMapper signatureInfoMapper;

    @PostMapping(value = "/record-books/aggregation")
    @Transactional
    @Operation(summary = "Создать агрегацию зачётных книг за временной отрезок")
    public ResponseEntity<AggregationWithStatusDto> createRecordBookAgg(@RequestParam("userId") UUID userId,
                                                                        @RequestParam("periodStart") LocalDate periodStart,
                                                                        @RequestParam("periodEnd") LocalDate periodEnd,
                                                                        @RequestParam("serializationFormat") SerializationFormat serializationFormat)
            throws JsonProcessingException {
        Optional<RecordBooksAggregation> latest = aggregationRepository.getLatestId();
        Long newId = latest.map(recordBooksAggregation -> recordBooksAggregation.getVersionedId().getId() + 1L).orElse(1L);

        byte[] content = getSerializedContent(periodStart, periodEnd);

        RecordBooksAggregation aggregation = new RecordBooksAggregation();
        aggregation.setVersionedId(new VersionedId(newId, 1L));
        aggregation.setCreatedAt(ZonedDateTime.now());
        aggregation.setPeriodEnd(periodEnd);
        aggregation.setPeriodStart(periodStart);
        aggregation.setAuthor(userId);
        aggregation.setSerializationFormat(serializationFormat);
        aggregation.setSerializedContent(content);


        AggregationStatus aggregationStatus = new AggregationStatus();
        aggregationStatus.setCreatedAt(aggregation.getCreatedAt());
        aggregationStatus.setAggregationId(newId);
        aggregationStatus.setAggregationVersion(1L);
        aggregationStatus.setSignatureValidationResult(SignatureValidationResultType.INVALID);
        aggregationStatus.setSignatureValidationReason("Отсутствует подпись");
        aggregationStatus.setIntegrityValidationResult(IntegrityValidationResultType.INVALID);
        aggregationStatus.setIntegrityValidationReason("Проверка целостности агрегации невозможна без наличия подписи" );

        aggregationRepository.save(aggregation);
        aggregationStatusRepository.save(aggregationStatus);

        RecordBooksAggregationDto result = aggregationMapper.toDto(aggregation);
        AggregationStatusDto status = aggregationStatusMapper.toDto(aggregationStatus);

        return ResponseEntity.ok(new AggregationWithStatusDto(result, status, null, null));
    }

    private byte[] getSerializedContent(LocalDate periodStart, LocalDate periodEnd) throws JsonProcessingException {
        List<Sheet> allSheets = sheetRepository.findAllByExamDateBetweenAndDeleted(periodStart, periodEnd, false);
        Collection<List<Sheet>> values = allSheets.stream().collect(Collectors.groupingBy(s -> s.getVersionedId().getId())).values();

        ArrayList<Sheet> latestSheets = new ArrayList<>();
        for (List<Sheet> list : values) {
            Sheet sheet = list.stream().max(Comparator.comparingLong(s -> s.getVersionedId().getVersion())).get();
            latestSheets.add(sheet);
        }

        ArrayList<SheetWithGradesDto> result = new ArrayList<>();
        for (Sheet sheet : latestSheets) {
            List<Grade> grades = gradeRepository.findAllBySheetIdAndSheetVersion(sheet.getVersionedId().getId(), sheet.getVersionedId().getVersion());
            SheetWithGradesDto withGradesDto = new SheetWithGradesDto(sheetMapper.toDto(sheet), gradeMapper.toDtos(grades));
            result.add(withGradesDto);
        }

        return recordBookSerializer.serialize(result);
    }

    @GetMapping(value = "/record-books/aggregation/{id}/validate")
    @Transactional
    @Operation(summary = "Обновить статус агрегации зачётных книг")
    public ResponseEntity<AggregationWithStatusDto> revalidateSignature(@PathVariable("id") Long aggregationId) throws IOException {
        RecordBooksAggregation aggregation = aggregationRepository.findLatest(aggregationId).get();
        AggregationStatus aggregationStatus = aggregationStatusRepository.findByAggregationIdAndAggregationVersion(aggregation.getVersionedId().getId(), aggregation.getVersionedId().getVersion()).get();
        SignatureInfo signatureInfo = signatureInfoRepository.findById(aggregation.getSignature()).get();
        CertificateInfo certificateInfo = certificateInfoRepository.findById(signatureInfo.getCertificate()).get();

        try {
            signatureValidator.validate(aggregation.getSerializedContent(), signatureInfo.getSignatureFile());
            aggregationStatus.setSignatureValidationReason(null);
            aggregationStatus.setSignatureValidationResult(SignatureValidationResultType.VALID);
        } catch (Exception e) {
            aggregationStatus.setSignatureValidationResult(SignatureValidationResultType.INVALID);
            aggregationStatus.setSignatureValidationReason("Не удалось проверить подпись. Детали: " + e.getMessage());
        }

        byte[] serializedContent = getSerializedContent(aggregation.getPeriodStart(), aggregation.getPeriodEnd());
        String current = DigestUtils.sha256Hex(serializedContent);



        aggregationStatus.setCreatedAt(ZonedDateTime.now());
        aggregationStatusRepository.save(aggregationStatus);


        return ResponseEntity.ok(new AggregationWithStatusDto(
                aggregationMapper.toDto(aggregation),
                aggregationStatusMapper.toDto(aggregationStatus),
                signatureInfoMapper.toDto(signatureInfo),
                certificateInfoMapper.toDto(certificateInfo))
        );
    }


    @GetMapping(value = "/record-books/aggregation/{id}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional
    @Operation(summary = "Скачать файл агрегации зачётных книг")
    public ResponseEntity<byte[]> getRecordBookAgg(@PathVariable("id") Long aggregationId) throws IOException {
        RecordBooksAggregation result = aggregationRepository.findLatest(aggregationId).get();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + aggregationId.toString() + ".json");

        return new ResponseEntity<>(result.getSerializedContent(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/record-books/aggregations")
    @Transactional
    @Operation(summary = "Получить все агрегации")
    public ResponseEntity<List<AggregationWithStatusDto>> getAllRecordBookAggs() {
        List<RecordBooksAggregation> all = aggregationRepository.findAll();
        ArrayList<AggregationWithStatusDto> result = new ArrayList<>();
        for (RecordBooksAggregation aggregation : all) {
            RecordBooksAggregationDto aggregationDto = aggregationMapper.toDto(aggregation);
            AggregationStatus aggregationStatus = aggregationStatusRepository.findByAggregationIdAndAggregationVersion(aggregation.getVersionedId().getId(), aggregation.getVersionedId().getVersion()).get();
            AggregationStatusDto aggregationStatusDto = aggregationStatusMapper.toDto(aggregationStatus);
            SignatureInfoDto signatureInfoDto = null;
            CertificateInfoDto certificateInfoDto = null;
            if (aggregation.getSignature() != null) {
                SignatureInfo signatureInfo = signatureInfoRepository.findById(aggregation.getSignature()).get();
                signatureInfoDto = signatureInfoMapper.toDto(signatureInfo);
                CertificateInfo certificateInfo = certificateInfoRepository.findById(signatureInfo.getCertificate()).get();
                certificateInfoDto = certificateInfoMapper.toDto(certificateInfo);

            }
            result.add(new AggregationWithStatusDto(aggregationDto, aggregationStatusDto, signatureInfoDto, certificateInfoDto));

        }

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/record-books/aggregation/{id}/signature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @Operation(summary = "Загрузить подписанный файл агрегации")
    public ResponseEntity<AggregationWithStatusDto> signRecordBookAgg(@PathVariable("id") Long aggregationId,
                                                                       @RequestParam("signatureType") SignatureType signatureType,
                                                                       @RequestPart(name = "file") MultipartFile file) {

        RecordBooksAggregation aggregation = aggregationRepository.findLatest(aggregationId).get();
        AggregationStatus aggregationStatus = aggregationStatusRepository.findByAggregationIdAndAggregationVersion(aggregationId, aggregation.getVersionedId().getVersion()).get();
        AggregationWithStatusDto result = new AggregationWithStatusDto();
        try {

            byte[] signature = file.getBytes();
            CAdESSignature cades = signatureValidator.validate(aggregation.getSerializedContent(), signature);

            X509Certificate certificate = extracted(cades);

            CertificateInfo certificateInfo = new CertificateInfo();
            certificateInfo.setId(UUID.randomUUID());
            certificateInfo.setType(CertificateType.X_509);
            certificateInfo.setCreatedAt(ZonedDateTime.now());
            certificateInfo.setNotBefore(certificate.getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            certificateInfo.setNotAfter(certificate.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            certificateInfo.setContent(certificate.getEncoded());
            certificateInfo.setSubjectInfo(certificate.getIssuerX500Principal().getName());
            certificateInfoRepository.save(certificateInfo);
            result.setCertificateInfo(certificateInfoMapper.toDto(certificateInfo));

            SignatureInfo signatureInfo = new SignatureInfo();
            signatureInfo.setType(signatureType);
            signatureInfo.setDigestAlgorithm(DigestAlgorithmType.UNKNOWN);
            signatureInfo.setCreatedAt(ZonedDateTime.now());
            signatureInfo.setId(UUID.randomUUID());
            signatureInfo.setSignatureFile(signature);
            signatureInfo.setCertificate(certificateInfo.getId());
            signatureInfo.setFileDigest(DigestUtils.sha256(file.getBytes()));
            signatureInfoRepository.save(signatureInfo);
            result.setSignatureInfo(signatureInfoMapper.toDto(signatureInfo));

            aggregation.setSignature(signatureInfo.getId());

            aggregationStatus.setSignatureValidationResult(SignatureValidationResultType.VALID);
            aggregationStatus.setSignatureValidationReason(null);
            aggregationStatus.setIntegrityValidationResult(IntegrityValidationResultType.VALID);
            aggregationStatus.setIntegrityValidationReason(null);
        } catch (Exception e) {
            aggregationStatus.setSignatureValidationResult(SignatureValidationResultType.INVALID);
            aggregationStatus.setSignatureValidationReason("Не удалось проверить подпись. Детали: " + e.getMessage());
            aggregationStatus.setIntegrityValidationResult(IntegrityValidationResultType.INVALID);
            aggregationStatus.setIntegrityValidationReason("Проверка целостности агрегации невозможна без наличия подписи" );
        }
        aggregationRepository.save(aggregation);
        aggregationStatusRepository.save(aggregationStatus);

        RecordBooksAggregationDto aggregationDto = aggregationMapper.toDto(aggregation);
        AggregationStatusDto statusDto = aggregationStatusMapper.toDto(aggregationStatus);
        result.setAggregation(aggregationDto);
        result.setStatus(statusDto);
        return ResponseEntity.ok(result);
    }


    private X509Certificate extracted(CAdESSignature cades) throws CertificateException, IOException {
        Collection<X509CertificateHolder> allCerts = cades.getCertificateStore().getMatches(new SignatureValidator.AllCertificatesSelector());
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        ArrayList<X509Certificate> result = new ArrayList<>();
        for (X509CertificateHolder c : allCerts) {
            result.add((X509Certificate) factory.generateCertificate(new ByteArrayInputStream(c.getEncoded())));
        }
        return result.get(0);
    }


}
