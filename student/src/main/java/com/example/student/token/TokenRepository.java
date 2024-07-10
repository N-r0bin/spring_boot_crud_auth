package com.example.student.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join Student s
      on t.student.id = s.id
      where s.id = :id and t.expiryDate > CURRENT_TIMESTAMP
      """)


    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);






}
