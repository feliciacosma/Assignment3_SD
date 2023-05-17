package com.example.demo.business;
import com.example.demo.model.Student;
import com.example.demo.model.iter.Iterator;
import com.example.demo.model.iter.student.StudentContainer;
import com.example.demo.persistance.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class StudentService implements Observer {

    @Autowired
    private StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //C - create operation
    public Student createStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    //R - read operation
    public List<Student> readStudent() {
        return studentRepository.findAll();
    }

    //U - update operation
    public Student updateStudent(Student student, Integer studentId) {
        Student a = studentRepository.findById(studentId).get();

        if (Objects.nonNull(student.getUsername()) && !"".equalsIgnoreCase(student.getUsername())) {
            a.setUsername(student.getUsername());
        }

        if (Objects.nonNull(student.getPassword()) && !"".equalsIgnoreCase(student.getPassword())) {
            a.setPassword(passwordEncoder.encode(student.getPassword()));
        }

        return studentRepository.save(a);
    }

    //D - delete operation
    public String deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
        return "Student deleted successfully.";
    }

    public Student findById(int id) throws NoSuchElementException {
        StudentContainer studentContainer = new StudentContainer(readStudent().toArray((Student[]::new)));
        Iterator<Student> iterator = studentContainer.getIterator();
        while(iterator.hasNext()){
            Student student = iterator.next();
            if(student.getStudentId() == id){
                return student;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void update(Observable o, Object arg){
        Student studentInfo = (Student) arg;
        updateStudent(studentInfo, studentInfo.getStudentId());
    }

}