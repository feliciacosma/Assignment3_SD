package com.example.demo.model.iter.student;

import com.example.demo.model.Student;
import com.example.demo.model.iter.Container;
import com.example.demo.model.iter.Iterator;

public class StudentContainer implements Container<Student> {

    private final Student[] students;
    public StudentContainer(Student[] students) {
        this.students = students;
    }
    @Override
    public Iterator<Student> getIterator() {
        return new StudentIterator();
    }

    private class StudentIterator implements Iterator<Student>{
        private int index;
        private StudentIterator(){
            this.index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < students.length;
        }

        @Override
        public Student next() {
            if (hasNext()){
                return students[index++];
            }
            return null;
        }
    }
}
