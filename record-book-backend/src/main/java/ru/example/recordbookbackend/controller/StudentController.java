package ru.example.recordbookbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.example.recordbookbackend.dto.StudentDto;
import ru.example.recordbookbackend.dto.UserInfoDto;
import ru.example.recordbookbackend.dto.controller.StudentWithUserInfoDto;
import ru.example.recordbookbackend.dto.mapper.StudentMapper;
import ru.example.recordbookbackend.dto.mapper.UserInfoMapper;
import ru.example.recordbookbackend.entity.*;
import ru.example.recordbookbackend.repository.StudentRepository;
import ru.example.recordbookbackend.repository.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "student-controller")
@RequiredArgsConstructor
public class StudentController {


    private final StudentRepository studentRepository;

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    private final StudentMapper mapper;


    @GetMapping("/students")
    @Transactional
    public ResponseEntity<List<StudentWithUserInfoDto>> getStudents() {
        List<Student> allTeachers = studentRepository.findAllByDeleted(false);
        List<StudentWithUserInfoDto> result = new ArrayList<>();
        for (Student teacher : allTeachers) {
            StudentWithUserInfoDto teacherDto = mapper.toUserDto(teacher);
            UserInfo userInfo = userInfoRepository.findById(teacher.getUserId()).get();
            UserInfoDto userInfoDto = userInfoMapper.toDto(userInfo);
            teacherDto.setUserInfoDto(userInfoDto);
            result.add(teacherDto);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/student")
    @Transactional
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        Student student = mapper.toEntity(studentDto);
        return ResponseEntity.ok(mapper.toDto(studentRepository.save(student)));
    }

    @PutMapping("/student/")
    @Transactional
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findByIdAndDeleted(studentDto.getId(), false);
        if (optionalStudent.isPresent()) {
            Student newUser = mapper.partialUpdate(studentDto, optionalStudent.get());
            return ResponseEntity.ok(mapper.toDto(studentRepository.save(newUser)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/student/{id}")
    @Transactional
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {

        Optional<Student> student = studentRepository.findByIdAndDeleted(id, false);
        if (student.isPresent()) {
            student.get().setDeleted(true);
            studentRepository.save(student.get());
        }
        return ResponseEntity.ok().build();
    }


}
