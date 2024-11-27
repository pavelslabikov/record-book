package ru.example.recordbookbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.CryptoPro.CAdES.CAdESSignature;
import ru.example.recordbookbackend.dto.RecordBooksAggregationDto;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.dto.mapper.*;
import ru.example.recordbookbackend.entity.*;
import ru.example.recordbookbackend.repository.*;
import ru.example.recordbookbackend.service.RecordBookSerializer;
import ru.example.recordbookbackend.service.SignatureValidator;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "record-book-controller", description = "Контроллер управления зачётными книгами")
@RequiredArgsConstructor
public class RecordBookController {

    private final SheetRepository sheetRepository;

    private final GradeRepository gradeRepository;


    private final SheetMapper sheetMapper;

    private final GradeMapper gradeMapper;


    private final ObjectMapper objectMapper;

    private final RecordBooksAggregationMapper aggregationMapper;

    private final RecordBooksAggregationRepository aggregationRepository;

    private final SignatureValidator signatureValidator;

    private final DeanEmployeeRepository deanEmployeeRepository;

    private final RecordBookSerializer recordBookSerializer;

    @PostMapping(value = "/record-books/aggregation")
    @Transactional
    @Operation(summary = "Создать агрегацию зачётных книг за временной отрезок")
    public ResponseEntity<RecordBooksAggregationDto> createRecordBookAgg(@RequestParam("periodStart") LocalDate periodStart,
                                                                         @RequestParam("periodEnd") LocalDate periodEnd)
            throws JsonProcessingException {


        List<Sheet> allSheets = sheetRepository.findAllByExamDateBetweenAndDeleted(periodStart, periodEnd, false);
        Collection<List<Sheet>> values = allSheets.stream().collect(Collectors.groupingBy(s -> s.getId().getSheetId())).values();

        ArrayList<Sheet> latestSheets = new ArrayList<>();
        for (List<Sheet> list : values) {
            Sheet sheet = list.stream().max(Comparator.comparingLong(s -> s.getId().getSheetVersion())).get();
            latestSheets.add(sheet);
        }

        ArrayList<SheetWithGradesDto> result = new ArrayList<>();
        for (Sheet sheet : latestSheets) {
            List<Grade> grades = gradeRepository.findAllBySheetIdAndSheetVersion(sheet.getId().getSheetId(), sheet.getId().getSheetVersion());
            SheetWithGradesDto withGradesDto = new SheetWithGradesDto(sheetMapper.toDto(sheet), gradeMapper.toDtos(grades));
            result.add(withGradesDto);
        }

        byte[] content = recordBookSerializer.serialize(result);
        RecordBooksAggregation aggregation = new RecordBooksAggregation();
        aggregation.setCreatedAt(ZonedDateTime.now());
        aggregation.setId(UUID.randomUUID());
        aggregation.setPeriodEnd(periodEnd);
        aggregation.setPeriodStart(periodStart);
        aggregation.setSignatureValidationResult(SignatureValidationResultType.INVALID);
        aggregation.setSignatureValidationReason("Отсутствует подпись");
        aggregation.setOriginalFile(content);
        aggregationRepository.save(aggregation);

        return ResponseEntity.ok(aggregationMapper.toDto(aggregation));
    }


    @GetMapping(value = "/record-books/aggregation/{id}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional
    @Operation(summary = "Скачать файл агрегации зачётных книг")
    public ResponseEntity<byte[]> getRecordBookAgg(@PathVariable("id") UUID aggregationId) throws IOException {
        byte[] result = aggregationRepository.findById(aggregationId).get().getOriginalFile();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + aggregationId.toString() + ".json");

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/record-books/aggregations")
    @Transactional
    @Operation(summary = "Получить все агрегации")
    public ResponseEntity<List<RecordBooksAggregationDto>> getAllRecordBookAggs() {
        List<RecordBooksAggregation> all = aggregationRepository.findAll();
        return ResponseEntity.ok(aggregationMapper.toDtos(all));
    }

    @PostMapping(value = "/record-books/aggregation/{id}/signature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @Operation(summary = "Загрузить подписанный файл агрегации")
    public ResponseEntity<RecordBooksAggregationDto> signRecordBookAgg(@PathVariable("id") UUID aggregationId,
                                                                       @RequestParam("deanId") Integer deanId,
                                                                       @RequestPart(name = "file") MultipartFile file) {

        RecordBooksAggregation aggregation = aggregationRepository.findById(aggregationId).get();
        DeanEmployee deanEmployee = deanEmployeeRepository.findById(deanId).get();
        try {
            byte[] signature = file.getBytes();
            CAdESSignature cades = signatureValidator.validate(aggregation.getOriginalFile(), signature, new ByteArrayInputStream(deanEmployee.getCertificate()));
            aggregation.setFileDigest(cades.getCAdESSignerInfo(0).getSignerInfo().getContentDigest());
            aggregation.setSignatureFile(signature);
            aggregation.setSignatureValidationResult(SignatureValidationResultType.VALID);
            aggregation.setSignatureValidationReason(null);
        } catch (Exception e) {
            aggregation.setSignatureValidationResult(SignatureValidationResultType.INVALID);
            aggregation.setSignatureValidationReason("Не удалось проверить подпись. Детали: " + e.getMessage());
        }
        aggregationRepository.save(aggregation);

        return ResponseEntity.ok(aggregationMapper.toDto(aggregation));
    }


}
