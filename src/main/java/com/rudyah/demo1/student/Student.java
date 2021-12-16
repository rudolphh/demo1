package com.rudyah.demo1.student;

import com.rudyah.demo1.api.validation.groups.OnInsert;
import com.rudyah.demo1.api.validation.groups.OnUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@Getter @Setter @ToString
@Entity
@Table(name = "students")
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_seq",
            sequenceName = "student_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "name is mandatory", groups = OnInsert.class)
    @Size(min = 3, message = "Name must be at least three characters", groups = { OnInsert.class, OnUpdate.class})
    private String name;

    @NotBlank(message = "email is mandatory", groups = OnInsert.class)
    @Email(groups = { OnInsert.class, OnUpdate.class})
    @Size(min = 5, message = "Email must be at least five characters", groups = { OnInsert.class, OnUpdate.class})
    private String email;

    @Past(groups = { OnInsert.class, OnUpdate.class })
    @DateTimeFormat(pattern = "uuuu-MM-dd")
    //@DateOfBirthConstraint(pattern = "uuuu-MM-dd", groups = { OnInsert.class, OnUpdate.class })
    private LocalDate dob;

    @Transient
    private Integer age;

    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
