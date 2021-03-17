package fms.repositories;

import fms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query("SELECT t FROM User t WHERE t.userID = ?1 and t.password = ?2")
    List<User> findByCredentials(String name, String password);
}