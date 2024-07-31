package lk.ijse.gdse.spring.spring_introduction.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //this annotation used to specify that the class is an entity and is mapped to a database table.
public class Blog {
    @Id //this annotation used to specify the primary key of an entity(Table).
    private int id = 1;
    private String title = "Spring Boot";
    private String content = "Spring Boot is a powerful framework for building web applications.";

}
