package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.repositories.UserRoleRepository;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    private RoleServiceImpl serviceToBeTested;

    @Mock
    UserRoleRepository mockRepository;



    @BeforeEach
    public void setUp(){
        serviceToBeTested = new RoleServiceImpl(mockRepository);
    }


    @Test
    public void testFindByRole(){
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserRoleEntity adminUser = new UserRoleEntity();
        adminUser.setRole(UserRole.ADMIN);

        Mockito.when(mockRepository.findByRole(UserRole.USER))
                .thenReturn(Optional.of(role));
       UserRoleEntity entity =serviceToBeTested.findByRole(UserRole.USER);
        Assertions.assertEquals(role.getRole(),entity.getRole());
    }
    @Test
    public void testFindByRoleVoid() throws IllegalStateException {
        Assertions.assertThrows(IllegalStateException.class,
                ()-> serviceToBeTested.findByRole(null)
        );
    }
}
