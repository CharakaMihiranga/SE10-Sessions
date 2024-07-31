package lk.ijse.gdse.spring.spring_introduction.controller;

import lk.ijse.gdse.spring.spring_introduction.entity.Blog;
import org.springframework.web.bind.annotation.*;

@RestController // This annotation is used to create a RESTful web service.
@RequestMapping("/blog") // http://localhost:8080/blog , this annotation is used to map web requests onto
// specific handler classes and/or handler methods.
public class BlogController {

    @GetMapping("/hello") // http://localhost:8080/blog/hello
    public String hello () {
        return "Hello Spring Boot";
    }

    @PostMapping("/number") // http://localhost:8080/blog/number
    public int returnNumber(){
        return 9;
    }

    @GetMapping("/data/{number}") // http://localhost:8080/blog/data/{any number you want}
    public int getNumber(@PathVariable int number){
        return number;
    }

    @GetMapping("/object") // http://localhost:8080/blog/object
    public Object getObject(){
        Blog blog = new Blog();
        return blog;
    }

}
