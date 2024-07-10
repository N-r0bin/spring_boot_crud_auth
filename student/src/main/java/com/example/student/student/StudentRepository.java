package com.example.student.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // find user by email
    @Query("SELECT s FROM Student s WHERE s.email = ?1") // executted when method called
    Optional<Student> findStudentByEmail(String email); // method signature
}
