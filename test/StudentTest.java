package com.example.demo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.demo.business.StudentService;
import com.example.demo.model.Role;
import com.example.demo.model.Student;
import com.example.demo.persistance.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    @Test
    public void testCreateStudent(){
        Student student = new Student(1, "iulia", "iulia4", Role.STUDENT);
        when(studentRepository.save(student)).thenReturn(student);
        Student savedStudent = studentService.createStudent(student);
        assertEquals(student,savedStudent);
    }

}