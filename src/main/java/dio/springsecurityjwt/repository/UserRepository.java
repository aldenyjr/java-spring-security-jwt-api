package dio.springsecurityjwt.repository;

import dio.springsecurityjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")
    public User findByUsername(@Param("username") String username);

    @Query("Select e from User e JOIN FETCH e.roles r WHERE r = 'USERS'")
    List<User> findUsersWithUserRole();

    boolean existsByUsername(String username);
}