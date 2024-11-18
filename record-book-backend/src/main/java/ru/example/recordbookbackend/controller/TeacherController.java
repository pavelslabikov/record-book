package ru.example.recordbookbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.example.recordbookbackend.dto.StudentDto;
import ru.example.recordbookbackend.dto.TeacherDto;
import ru.example.recordbookbackend.dto.UserInfoDto;
import ru.example.recordbookbackend.dto.controller.TeacherWithUserInfoDto;
import ru.example.recordbookbackend.dto.mapper.StudentMapper;
import ru.example.recordbookbackend.dto.mapper.TeacherMapper;
import ru.example.recordbookbackend.dto.mapper.UserInfoMapper;
import ru.example.recordbookbackend.entity.Student;
import ru.example.recordbookbackend.entity.Teacher;
import ru.example.recordbookbackend.entity.UserInfo;
import ru.example.recordbookbackend.repository.TeacherRepository;
import ru.example.recordbookbackend.repository.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "teacher-controller")
@RequiredArgsConstructor
public class TeacherController {


    private final TeacherRepository teacherRepository;

    private final UserInfoRepository userInfoRepository;

    private final TeacherMapper mapper;

    private final UserInfoMapper userInfoMapper;


    @GetMapping("/teachers")
    @Transactional
    public ResponseEntity<List<TeacherWithUserInfoDto>> getTeachers() {
        List<Teacher> allTeachers = teacherRepository.findAllByDeleted(false);
        List<TeacherWithUserInfoDto> result = new ArrayList<>();
        for (Teacher teacher : allTeachers) {
            TeacherWithUserInfoDto teacherDto = mapper.toUserDto(teacher);
            UserInfo userInfo = userInfoRepository.findById(teacher.getUserId()).get();
            UserInfoDto userInfoDto = userInfoMapper.toDto(userInfo);
            teacherDto.setUserInfoDto(userInfoDto);
            result.add(teacherDto);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/teacher")
    @Transactional
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto studentDto) {
        Teacher student = mapper.toEntity(studentDto);
        return ResponseEntity.ok(mapper.toDto(teacherRepository.save(student)));
    }

    @PutMapping("/teacher/")
    @Transactional
    public ResponseEntity<TeacherDto> updateTeacher(@RequestBody TeacherDto studentDto) {
        Optional<Teacher> optionalStudent = teacherRepository.findByIdAndDeleted(studentDto.getId(), false);
        if (optionalStudent.isPresent()) {
            Teacher newUser = mapper.partialUpdate(studentDto, optionalStudent.get());
            return ResponseEntity.ok(mapper.toDto(teacherRepository.save(newUser)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/teacher/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTeacher(@PathVariable Integer id) {

        Optional<Teacher> student = teacherRepository.findByIdAndDeleted(id, false);
        if (student.isPresent()) {
            student.get().setDeleted(true);
            teacherRepository.save(student.get());
        }
        return ResponseEntity.ok().build();
    }


}
