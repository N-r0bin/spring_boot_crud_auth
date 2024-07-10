package com.example.student.student;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.student.user.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public List<Student> getStudents() {
        return repository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = repository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }

        repository.save(student);
    }

    public void deleteStudent(Long studentId) {
        repository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email, String dob, Principal principal) {
        Student student = repository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));

        boolean isModified = false;

        if (name != null && !name.equals(student.getName())) {
            student.setName(name);
            isModified = true;
        }

        if (email != null && !email.equals(student.getEmail())) {
            Optional<Student> studentOptional = repository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email taken");
            }
            student.setEmail(email);
            isModified = true;
        }

        if (dob != null && !dob.isEmpty()) {
            try {
                LocalDate parsedDob = LocalDate.parse(dob, DateTimeFormatter.ISO_DATE);
                student.setDob(parsedDob);
                isModified = true;
            } catch (DateTimeParseException e) {
                throw new IllegalStateException("Invalid date format: " + dob);
            }
        }

        System.out.println("IS MODIFIED" + isModified);
        if (isModified) {
            student.setLastModifiedDate(LocalDateTime.now());
            student.setLastModifiedBy(principal.getName());

            logger.info("Saving updated student: {}", student);
            repository.save(student);
        } else {
            logger.info("No changes detected for student: {}", student);
        }



    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (Student) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }
}