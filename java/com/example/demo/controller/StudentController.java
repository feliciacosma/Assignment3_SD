package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.business.StudentService;
import com.example.demo.model.StudentUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableMethodSecurity
public class StudentController {

    @Autowired
    private StudentService studentService;

    //C - create operation
    @PostMapping("/student/createStudent")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    //R - read operation
    @GetMapping("/student/readStudent")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Student> readStudent() {
        return studentService.readStudent();
    }

    //U - update operation
    @PutMapping("/student/updateStudent/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Student updateStudent(@RequestBody StudentUpdate student, @PathVariable("studentId") Integer studentId) {
        student.addObserver(studentService);
        student.setStudentId(studentId);
        return studentService.findById(studentId);
    }

    //D - delete operation
    @DeleteMapping("/student/deleteStudent/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteStudent(@PathVariable("studentId") Integer studentId) {
        studentService.deleteStudent(studentId);
        return "Student deleted successfully.";
    }

}