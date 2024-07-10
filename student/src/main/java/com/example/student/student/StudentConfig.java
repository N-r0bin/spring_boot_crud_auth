//package com.example.student.student;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class StudentConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository repository) {
//        return args -> {
//            Student noha = Student.builder()
//                    .name("Noha")
//                    .email("noha.ali@gmail.com")
//                    .password("1234")
//                    .dob(LocalDate.of(2002, Month.OCTOBER, 10))
//                    .role(Role.TA)
//                    .build();
//            Student alex = Student.builder()
//                    .name("Alex")
//                    .email("alex@gmail.com")
//                    .password("1234")
//                    .dob(LocalDate.of(2001, Month.DECEMBER, 20))
//                    .role(Role.RA)
//                    .build();
//
//            repository.saveAll(List.of(noha, alex));
//        };
//    }
//}
