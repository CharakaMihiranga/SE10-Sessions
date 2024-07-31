package lk.ijse.gdse.spring.spring_introduction.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Blog {
    @Id
    private int id = 1;
    private String title = "Spring Boot";
    private String content = "Spring Boot is a powerful framework for building web applications.";

}
