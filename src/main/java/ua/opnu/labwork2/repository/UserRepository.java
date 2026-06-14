package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName,
            String lastName,
            String email
    );
}
