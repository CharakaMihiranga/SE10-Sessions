package lk.ijse.gdse.spring.spring_introduction.repository;

import lk.ijse.gdse.spring.spring_introduction.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //this annotation used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface BlogRepository extends JpaRepository<Blog,Integer>{ //<Blog,Integer> Blog is the name of the entity class and Integer is the type of the primary key of that entity class.

}
