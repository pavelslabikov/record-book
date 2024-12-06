package ru.example.recordbookbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.recordbookbackend.dto.GradeDto;
import ru.example.recordbookbackend.dto.SheetDto;
import ru.example.recordbookbackend.dto.controller.NormalizedRecordBookDto;
import ru.example.recordbookbackend.dto.controller.SheetWithGradesDto;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.Teacher;
import ru.example.recordbookbackend.entity.UserInfo;
import ru.example.recordbookbackend.repository.StudentRepository;
import ru.example.recordbookbackend.repository.TeacherRepository;
import ru.example.recordbookbackend.repository.UserInfoRepository;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordBookSerializer {
    private final UserInfoRepository userInfoRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    private final ObjectMapper objectMapper;

    public byte[] serialize(List<SheetWithGradesDto> list) throws JsonProcessingException {
        Map<Integer, NormalizedRecordBookDto> result = new HashMap<>();
        for (SheetWithGradesDto sheetWithGradesDto : list) {
            SheetDto sheetDto = sheetWithGradesDto.getSheetDto();
            List<GradeDto> grades = sheetWithGradesDto.getGrades();
            switch (sheetDto.getType()) {
                case DIPLOMA -> {
                    for (GradeDto grade : grades) {
                        NormalizedRecordBookDto book = result.getOrDefault(grade.getStudentId(), createBook(grade.getStudentId()));
                        NormalizedRecordBookDto.Diploma diploma = new NormalizedRecordBookDto.Diploma();
                        diploma.setForm(sheetDto.getDiplomaForm());
                        diploma.setTopic(sheetDto.getTopic());
                        diploma.setGrade(grade.getValue());
                        diploma.setDefenseDate(sheetDto.getDiplomaDefenseDate());
                        diploma.setProtocolDate(sheetDto.getProtocolDate());
                        diploma.setProtocolNumber(sheetDto.getProtocolNumber());
                        book.setDiploma(diploma);
                    }
                    break;
                }
                case MIDTERM_ASSESSMENT -> {
                    for (GradeDto grade : grades) {
                        NormalizedRecordBookDto book = result.getOrDefault(grade.getStudentId(), createBook(grade.getStudentId()));

                        Teacher teacher = teacherRepository.findByIdAndDeleted(sheetDto.getTeacherId(), false).get();
                        UserInfo userInfo = userInfoRepository.findByIdAndDeleted(teacher.getUserId(), false).get();
                        List<String> infoList = new ArrayList<>();
                        infoList.add(sheetDto.getSubjectName());
                        infoList.add(sheetDto.getAcademicYear());
                        infoList.add(sheetDto.getCreditUnits().toString());
                        infoList.add(grade.getValue());
                        infoList.add(sheetDto.getExamDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        infoList.add(String.format("%s.%c.%c", userInfo.getSurname(), userInfo.getName().toCharArray()[0], userInfo.getPatronymic().toCharArray()[0]));
                        String assessment = String.join("|", infoList);

                        if (book.getMidtermAssessmentResults() == null) {
                            book.setMidtermAssessmentResults(new ArrayList<String>());
                        }
                        book.getMidtermAssessmentResults().add(assessment);
                        result.put(grade.getStudentId(), book);
                    }
                    break;
                }

            }
        }
        List<NormalizedRecordBookDto> collect = result.values().stream()
                .sorted(Comparator.comparing(NormalizedRecordBookDto::getStudentIdCard, UUID::compareTo))
                .collect(Collectors.toList());
        return objectMapper.writeValueAsBytes(collect);
    }


    private NormalizedRecordBookDto createBook(Integer studentId) {
        NormalizedRecordBookDto bookDto = new NormalizedRecordBookDto();
        Student student = studentRepository.findByIdAndDeleted(studentId, false).get();
        UserInfo userInfo = userInfoRepository.findByIdAndDeleted(student.getUserId(), false).get();
        bookDto.setName(userInfo.getName());
        bookDto.setPatronymic(userInfo.getPatronymic());
        bookDto.setSurname(userInfo.getSurname());
        bookDto.setStudentIdCard(student.getIdCardNumber());
        bookDto.setRecordBookId(student.getGradeBookNumber());
        bookDto.setCourseNumber(student.getCourseNumber());

        return bookDto;
    }
}
