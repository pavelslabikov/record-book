package ru.example.recordbookbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.dto.mapper.GradeMapper;
import ru.example.recordbookbackend.dto.mapper.SheetMapper;
import ru.example.recordbookbackend.entity.*;
import ru.example.recordbookbackend.repository.GradeRepository;
import ru.example.recordbookbackend.repository.SheetChangelogRepository;
import ru.example.recordbookbackend.repository.SheetRepository;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "sheet-controller")
@RequiredArgsConstructor
public class SheetController {

    private final SheetRepository sheetRepository;

    private final GradeRepository gradeRepository;

    private final SheetChangelogRepository sheetChangelogRepository;

    private final SheetMapper sheetMapper;

    private final GradeMapper gradeMapper;


    @GetMapping("/sheets")
    @Transactional
    public ResponseEntity<List<SheetWithGradesDto>> getSheets() {
        List<SheetWithGradesDto> result = new ArrayList<>();
        Set<Long> sheetIds = sheetRepository.findAllByDeleted(false).stream().map(s -> s.getId().getSheetId()).collect(Collectors.toSet());
        for (Long sheetId : sheetIds) {
            Sheet latest = sheetRepository.findLatest(sheetId).get();
            SheetWithGradesDto sheetWithGradesDto = sheetMapper.toSheetWithGradesDto(latest);
            List<Grade> grades = gradeRepository.findAllBySheetIdAndSheetVersion(latest.getId().getSheetId(), latest.getId().getSheetVersion());
            sheetWithGradesDto.setGrades(gradeMapper.toDtos(grades));
            result.add(sheetWithGradesDto);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/sheet")
    @Transactional
    public ResponseEntity<SheetWithGradesDto> createSheet(@RequestBody SheetWithGradesDto sheetWithGradesDto,
                                                          @RequestParam("userId") UUID userId) {
        Optional<Sheet> latestSheet = sheetRepository.findLatest(sheetWithGradesDto.getSheetDto().getSheetId());
        Long newVersion = latestSheet.map(sheet -> sheet.getId().getSheetVersion() + 1L).orElse(1L);

        Sheet sheetEntity = sheetMapper.toEntity(sheetWithGradesDto.getSheetDto());
        sheetEntity.setCreatedAt(ZonedDateTime.now());
        VersionedId id = new VersionedId();
        id.setSheetId(sheetWithGradesDto.getSheetDto().getSheetId());
        id.setSheetVersion(newVersion);
        sheetEntity.setId(id);
        sheetRepository.save(sheetEntity);

        List<Grade> grades = gradeMapper.toEntities(sheetWithGradesDto.getGrades());
        for (Grade grade : grades) {
            grade.setCreatedAt(ZonedDateTime.now());
            grade.setSheetVersion(newVersion);
            grade.setSheetId(id.getSheetId());
        }
        gradeRepository.saveAll(grades);

        SheetChangelog sheetChangelog = new SheetChangelog();
        sheetChangelog.setCreatedAt(ZonedDateTime.now());
        sheetChangelog.setEntityId(id.getSheetId());
        sheetChangelog.setAuthor(userId);
        sheetChangelog.setNewVersion(newVersion);
        if (latestSheet.isEmpty() || latestSheet.get().getDeleted()) {
            sheetChangelog.setOperation(OperationType.CREATE);
        } else {
            sheetChangelog.setOperation(OperationType.UPDATE);
            sheetChangelog.setOldVersion(newVersion - 1);
        }
        sheetChangelogRepository.save(sheetChangelog);

        return ResponseEntity.ok(sheetWithGradesDto);
    }

    @DeleteMapping("/sheet/{sheetId}")
    @Transactional
    public ResponseEntity<Void> deleteSheet(@PathVariable Long sheetId, @RequestParam("userId") UUID userId) {
        Sheet sheet = sheetRepository.findLatest(sheetId).get();

        List<Sheet> toDelete = sheetRepository.findAllByIdSheetId(sheetId);
        toDelete.forEach(s -> s.setDeleted(true));
        sheetRepository.saveAll(toDelete);

        List<Grade> grades = gradeRepository.findAllBySheetId(sheetId);
        grades.forEach(s -> s.setDeleted(true));
        gradeRepository.saveAll(grades);



        SheetChangelog sheetChangelog = new SheetChangelog();
        sheetChangelog.setCreatedAt(ZonedDateTime.now());
        sheetChangelog.setOperation(OperationType.DELETE);
        sheetChangelog.setEntityId(sheetId);
        sheetChangelog.setOldVersion(sheet.getId().getSheetVersion());
        sheetChangelog.setAuthor(userId);
        sheetChangelogRepository.save(sheetChangelog);

        return ResponseEntity.ok().build();
    }


}
