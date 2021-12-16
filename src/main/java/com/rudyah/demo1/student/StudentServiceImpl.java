package com.rudyah.demo1.student;

import com.rudyah.demo1.api.error.exceptions.EmailExistsException;
import com.rudyah.demo1.api.error.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student){
        boolean exists = studentRepository.existsByEmail(student.getEmail());
        if(exists){
            throw new EmailExistsException("Email already exists");
        }
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(Long studentId){
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new StudentNotFoundException("Student with id: " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    @Transactional
    public Student updateStudent(Long studentId, Student student) {
        Student currentStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    throw new StudentNotFoundException("Student not found");
                });

        String name = student.getName();
        String email = student.getEmail();
        LocalDate dob = student.getDob();

        if(name != null && !name.isEmpty() && !Objects.equals(name, currentStudent.getName())){
            currentStudent.setName(name);
        }

        if(email != null && !email.isEmpty() && !Objects.equals(email, currentStudent.getEmail())){
            if(studentRepository.existsByEmail(email)){
                throw new EmailExistsException("Email already exists");
            }
            currentStudent.setEmail(email);
        }

        if(dob != null && !Objects.equals(dob, currentStudent.getDob())){
            currentStudent.setDob(dob);
        }

        return currentStudent;
    }
}
