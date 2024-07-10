package com.example.student.student;

import com.example.student.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student implements UserDetails {
    @Getter
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;

    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String email;
    @Setter
    private String password;

//    @Setter
//    @Getter
    private LocalDate dob;

   // @Transient

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens;

    @Setter
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Setter
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

//    @PostLoad
//    @PostPersist
//    @PostUpdate
    public Integer getAge() {
        if (this.dob == null) {
            return null;
        }
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
//   @Override
    public void setAge(Integer age) {
       // this.age = age;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
