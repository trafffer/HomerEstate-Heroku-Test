package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.repositories.UserRepository;
import bg.softuni.homerestate.services.HomerDBUserService;
import bg.softuni.homerestate.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserServiceImpl serviceToBeTested;

    @Mock
    UserRepository mockUserRepository;
    @Mock
    RoleService mockRoleService;
    @Mock
    ModelMapper mapper;
    @Mock
    PasswordEncoder encoder;
    @Mock
    HomerDBUserService MockhomerDBUserService;
    @Mock
    Authentication mockAuthentication;


    @BeforeEach
    public void setUp(){
        serviceToBeTested = new UserServiceImpl(
                mockUserRepository,mockRoleService,mapper,encoder,MockhomerDBUserService);
    }


    @Test
    public void testUserExist(){
        UserEntity user = new UserEntity();
        user.setUsername("Nasko").setId(1L);
        Mockito.when(mockUserRepository.findByUsername("Nasko")).thenReturn(Optional.of(user));
        boolean actual = serviceToBeTested.userExist("Nasko");
        Assertions.assertTrue(actual);

    }

    @Test
    public void testFindByUsername(){
        UserEntity user = new UserEntity();
        user.setUsername("Nasko").setId(1L);
        Mockito.when(mockUserRepository.findByUsername("Nasko")).thenReturn(Optional.of(user));
        UserEntity actual = serviceToBeTested.findByUsername("Nasko");
        String usernameActual = actual.getUsername();
        Assertions.assertEquals("Nasko",usernameActual);
    }

    @Test
    public void testChangeRole(){
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserRoleEntity adminUser = new UserRoleEntity();
        adminUser.setRole(UserRole.ADMIN);
        List<UserRoleEntity>roles=List.of(role,adminUser);

        UserEntity user = new UserEntity();
        user.setUsername("Nasko").setPassword("12345")
                .setRoles(List.of(role,adminUser)).setId(1L);
        Mockito.when(mockUserRepository.findByUsername("Nasko")).thenReturn(Optional.of(user));
        Mockito.when(mockRoleService.findByRole(UserRole.USER)).thenReturn(role);
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        serviceToBeTested.changeRole("Nasko","USER");
        Mockito.verify(mockUserRepository,Mockito.times(1)).findByUsername("Nasko");
        Mockito.verify(mockRoleService,Mockito.times(1)).findByRole(UserRole.USER);
        Mockito.verify(mockUserRepository,Mockito.times(1)).save(user);
    }

    @Test
    public void testChangeRoleAdmin() {
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserRoleEntity adminUser = new UserRoleEntity();
        adminUser.setRole(UserRole.ADMIN);
        List<UserRoleEntity> roles = List.of(role, adminUser);

        UserEntity user = new UserEntity();
        user.setUsername("Nasko").setPassword("12345")
                .setRoles(List.of(role, adminUser)).setId(1L);
        Mockito.when(mockUserRepository.findByUsername("Nasko")).thenReturn(Optional.of(user));
        Mockito.when(mockRoleService.getRoles()).thenReturn(roles);
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        serviceToBeTested.changeRole("Nasko", "ADMIN");
        Mockito.verify(mockUserRepository, Mockito.times(1)).findByUsername("Nasko");
        Mockito.verify(mockRoleService, Mockito.times(1)).getRoles();
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(user);

    }

}
