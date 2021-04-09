package bg.softuni.homerestate.repositories;

import bg.softuni.homerestate.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    @Query("SELECT u.username FROM UserEntity u " +
            "order by u.username ")
    List<String>findAllUsernames();
}
