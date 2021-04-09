package bg.softuni.homerestate.repositories;

import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long> {
    Optional<UserRoleEntity> findByRole(UserRole user);
}
