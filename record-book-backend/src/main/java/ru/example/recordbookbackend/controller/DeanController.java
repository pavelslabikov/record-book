package ru.example.recordbookbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.example.recordbookbackend.dto.DeanEmployeeDto;
import ru.example.recordbookbackend.dto.TeacherDto;
import ru.example.recordbookbackend.dto.mapper.DeanEmployeeMapper;
import ru.example.recordbookbackend.dto.mapper.TeacherMapper;
import ru.example.recordbookbackend.entity.DeanEmployee;
import ru.example.recordbookbackend.entity.Teacher;
import ru.example.recordbookbackend.repository.DeanEmployeeRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "dean-controller", description = "Контроллер управления сотрудниками деканата")
@RequiredArgsConstructor
public class DeanController {


    private final DeanEmployeeRepository deanEmployeeRepository;

    private final DeanEmployeeMapper mapper;


    @GetMapping("/deans")
    @Transactional
    public ResponseEntity<List<DeanEmployeeDto>> getDeans() {
        return ResponseEntity.ok(mapper.toDtos(deanEmployeeRepository.findAllByDeleted(false)));
    }

    @PostMapping("/dean")
    @Transactional
    public ResponseEntity<DeanEmployeeDto> createTeacher(@RequestBody DeanEmployeeDto studentDto) {
        DeanEmployee student = mapper.toEntity(studentDto);
        return ResponseEntity.ok(mapper.toDto(deanEmployeeRepository.save(student)));
    }

    @PutMapping("/dean/")
    @Transactional
    public ResponseEntity<DeanEmployeeDto> updateTeacher(@RequestBody DeanEmployeeDto studentDto) {
        Optional<DeanEmployee> optionalStudent = deanEmployeeRepository.findByIdAndDeleted(studentDto.getId(), false);
        if (optionalStudent.isPresent()) {
            DeanEmployee newUser = mapper.partialUpdate(studentDto, optionalStudent.get());
            return ResponseEntity.ok(mapper.toDto(deanEmployeeRepository.save(newUser)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/dean/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTeacher(@PathVariable Integer id) {

        Optional<DeanEmployee> student = deanEmployeeRepository.findByIdAndDeleted(id, false);
        if (student.isPresent()) {
            student.get().setDeleted(true);
            deanEmployeeRepository.save(student.get());
        }
        return ResponseEntity.ok().build();
    }

//    @PostMapping(value = "/dean/{id}/certificate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Transactional
//    @Operation(summary = "Загрузить файл сертификата, принадлежащего сотруднику деканата")
//    public ResponseEntity<Void> uploadDeanCertificate(@PathVariable Integer id, @RequestPart(name = "file") MultipartFile file) throws IOException {
//        DeanEmployee deanEmployee = deanEmployeeRepository.findByIdAndDeleted(id, false).get();
//        deanEmployee.setCertificate(file.getBytes());
//        deanEmployeeRepository.save(deanEmployee);
//        return ResponseEntity.ok().build();
//    }


}
