package ru.example.recordbookbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.example.recordbookbackend.dto.SheetChangelogDto;
import ru.example.recordbookbackend.dto.controller.DetailedSheetChangelogDto;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.dto.mapper.GradeMapper;
import ru.example.recordbookbackend.dto.mapper.SheetChangelogMapper;
import ru.example.recordbookbackend.dto.mapper.SheetMapper;
import ru.example.recordbookbackend.dto.mapper.UserInfoMapper;
import ru.example.recordbookbackend.entity.*;
import ru.example.recordbookbackend.repository.GradeRepository;
import ru.example.recordbookbackend.repository.SheetChangelogRepository;
import ru.example.recordbookbackend.repository.SheetRepository;
import ru.example.recordbookbackend.repository.UserInfoRepository;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "sheet-changelog-controller")
@RequiredArgsConstructor
public class SheetChangelogController {

    private final SheetRepository sheetRepository;

    private final GradeRepository gradeRepository;

    private final SheetChangelogRepository sheetChangelogRepository;

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    private final SheetMapper sheetMapper;

    private final GradeMapper gradeMapper;

    private final SheetChangelogMapper sheetChangelogMapper;


    @GetMapping("/sheet/{sheetId}/changelogs")
    @Transactional
    public ResponseEntity<List<SheetChangelogDto>> getSheetChangelogs(@PathVariable Long sheetId) {
        List<SheetChangelog> changelogs = sheetChangelogRepository.findAllByEntityIdOrderByCreatedAtAsc(sheetId);
        return ResponseEntity.ok(sheetChangelogMapper.toDtos(changelogs));
    }

    @GetMapping("/sheet/{sheetId}/changelog/{changelogId}")
    @Transactional
    public ResponseEntity<DetailedSheetChangelogDto> getSheetChangelog(@PathVariable Long sheetId,
                                                                       @PathVariable Long changelogId) {
        SheetChangelog sheetChangelog = sheetChangelogRepository.findById(changelogId).get();
        DetailedSheetChangelogDto detailed = sheetChangelogMapper.toSheetWithGradesDto(sheetChangelog);
        UserInfo userInfo = userInfoRepository.findById(sheetChangelog.getAuthor()).get();
        detailed.setUserInfoDto(userInfoMapper.toDto(userInfo));

        Sheet oldSheet = sheetRepository.findById(new VersionedId(sheetId, sheetChangelog.getOldVersion())).get();
        SheetWithGradesDto oldDto = sheetMapper.toSheetWithGradesDto(oldSheet);
        List<Grade> oldGrades = gradeRepository.findAllBySheetIdAndSheetVersion(sheetId, sheetChangelog.getOldVersion());
        oldDto.setGrades(gradeMapper.toDtos(oldGrades));
        detailed.setOldSheet(oldDto);

        Sheet newSheet = sheetRepository.findById(new VersionedId(sheetId, sheetChangelog.getNewVersion())).get();
        SheetWithGradesDto newDto = sheetMapper.toSheetWithGradesDto(newSheet);
        List<Grade> newGrades = gradeRepository.findAllBySheetIdAndSheetVersion(sheetId, sheetChangelog.getNewVersion());
        newDto.setGrades(gradeMapper.toDtos(newGrades));
        detailed.setNewSheet(newDto);



        return ResponseEntity.ok(detailed);
    }


}
