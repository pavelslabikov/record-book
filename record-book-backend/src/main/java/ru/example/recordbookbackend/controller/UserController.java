package ru.example.recordbookbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.example.recordbookbackend.dto.UserInfoDto;
import ru.example.recordbookbackend.dto.controller.UserInfoWithPasswordDto;
import ru.example.recordbookbackend.dto.mapper.UserInfoMapper;
import ru.example.recordbookbackend.entity.*;
import ru.example.recordbookbackend.repository.DeanEmployeeRepository;
import ru.example.recordbookbackend.repository.StudentRepository;
import ru.example.recordbookbackend.repository.TeacherRepository;
import ru.example.recordbookbackend.repository.UserInfoRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "user-controller", description = "Контроллер управления пользователями")
@RequiredArgsConstructor
public class UserController {
    private final UserInfoRepository userInfoRepository;

    private final TeacherRepository teacherRepository;

    private final DeanEmployeeRepository deanEmployeeRepository;

    private final StudentRepository studentRepository;

    private final UserInfoMapper mapper;


    @GetMapping("/users")
    @Transactional
    public ResponseEntity<List<UserInfoDto>> getUsers() {
        return ResponseEntity.ok(mapper.toDtos(userInfoRepository.findAllByDeleted(false)));
    }

    @PostMapping("/user")
    @Transactional
    public ResponseEntity<UserInfoDto> createUser(@RequestBody UserInfoWithPasswordDto userInfoWithPasswordDto) {

        UserInfo userInfo = mapper.toEntity(userInfoWithPasswordDto.getUserInfoDto());
        userInfo.setCreatedAt(ZonedDateTime.now());
        userInfo.setSystemRole(SystemRoleType.USER);
        userInfo.setPassword(userInfoWithPasswordDto.getPassword());

        userInfoRepository.save(userInfo);
        return ResponseEntity.ok(mapper.toDto(userInfo));
    }

    @PutMapping("/user/")
    @Transactional
    public ResponseEntity<UserInfoDto> updateUser(@RequestBody UserInfoDto userInfoDto) {
        Optional<UserInfo> userInfo = userInfoRepository.findByIdAndDeleted(userInfoDto.getId(), false);
        if (userInfo.isPresent()) {
            UserInfo newUser = mapper.partialUpdate(userInfoDto, userInfo.get());
            return ResponseEntity.ok(mapper.toDto(userInfoRepository.save(newUser)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        Optional<UserInfo> byId = userInfoRepository.findByIdAndDeleted(id, false);
        if (byId.isPresent()) {
            byId.get().setDeleted(true);
            userInfoRepository.save(byId.get());
        }

        Optional<Teacher> teacher = teacherRepository.findByUserIdAndDeleted(id, false);
        if (teacher.isPresent()) {
            teacher.get().setDeleted(true);
            teacherRepository.save(teacher.get());
        }

        Optional<Student> student = studentRepository.findByUserIdAndDeleted(id, false);
        if (student.isPresent()) {
            student.get().setDeleted(true);
            studentRepository.save(student.get());
        }

        Optional<DeanEmployee> dean = deanEmployeeRepository.findByUserIdAndDeleted(id, false);
        if (dean.isPresent()) {
            dean.get().setDeleted(true);
            deanEmployeeRepository.save(dean.get());
        }


        return ResponseEntity.ok().build();
    }


}
