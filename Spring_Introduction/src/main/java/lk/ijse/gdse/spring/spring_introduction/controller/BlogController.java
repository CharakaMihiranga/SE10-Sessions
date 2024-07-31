package lk.ijse.gdse.spring.spring_introduction.controller;

import lk.ijse.gdse.spring.spring_introduction.entity.Blog;
import lk.ijse.gdse.spring.spring_introduction.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // This annotation is used to create a RESTful web service.
@RequestMapping("/blog") // http://localhost:8080/blog , this annotation is used to map web requests onto
// specific handler classes and/or handler methods.
public class BlogController {

    @Autowired // This annotation is used to auto wire bean on the setter method.
    private BlogRepository blogRepository;


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

    // ** Save data to the database
    @PostMapping("/savePost")
    public String savePost(@RequestBody Blog blog){
        blogRepository.save(blog);
        return "Blog saved successfully!";
    }

    // ** Get all the data from the database
    @GetMapping("/getAllPosts")
    public Iterable<Blog> getAllPosts(){ //Iterable can be used to loop through all the elements in a collection.
        // it is a generic interface, and it is used to represent a collection of objects.
        return blogRepository.findAll();
    }


}
