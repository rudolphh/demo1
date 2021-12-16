package com.rudyah.demo1.student;

import com.rudyah.demo1.api.response.models.ApiResponse;
import com.rudyah.demo1.api.validation.groups.OnInsert;
import com.rudyah.demo1.api.validation.groups.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents(){
        List<Student> students = studentService.getAllStudents();

        ApiResponse<List<Student>> apiResponse =
                new ApiResponse<>(true, HttpStatus.OK,
                        "All students", students);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Student>> addStudent(@Validated(OnInsert.class) @RequestBody Student student){

        ApiResponse<Student> apiResponse;
        Student createdStudent = studentService.addStudent(student);

        apiResponse = new ApiResponse<>(
                true, HttpStatus.CREATED,
                "Student added successfully", createdStudent);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<ApiResponse<?>> removeStudent(@PathVariable("studentId") Long studentId){

        studentService.removeStudent(studentId);

        ApiResponse<Object> apiResponse = new ApiResponse<>(
                true, HttpStatus.OK,
                "Student with id: " + studentId + " deleted successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable("studentId") Long studentId,
                                                              @Validated(OnUpdate.class) @RequestBody(required = false) Student student) {

        Student updatedStudent = studentService.updateStudent(studentId, student);

        ApiResponse<Student> apiResponse = new ApiResponse<>(
                true, HttpStatus.OK,
                "Student with id: " + studentId + " successfully updated", updatedStudent);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
