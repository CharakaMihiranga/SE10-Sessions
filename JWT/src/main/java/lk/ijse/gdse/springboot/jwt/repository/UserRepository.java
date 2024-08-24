package lk.ijse.gdse.springboot.jwt.repository;

import lk.ijse.gdse.springboot.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    boolean existsByEmail(String username);
    int deleteAllByEmail(String username);
}
