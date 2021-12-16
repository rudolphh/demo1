package com.rudyah.demo1.student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();
    Student addStudent(Student student);
    void removeStudent(Long studentId);
    Student updateStudent(Long studentId, Student student);
}
