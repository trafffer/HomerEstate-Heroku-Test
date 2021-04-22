package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.models.service.UserServiceModel;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void setUp() {
        serviceToBeTested = new UserServiceImpl(
                mockUserRepository, mockRoleService, mapper, encoder, MockhomerDBUserService);
    }


    @Test
    public void testUserExist() {
        UserEntity user = new UserEntity();
        user.setUsername("Nasko").setId(1L);
        Mockito.when(mockUserRepository.findByUsername("Nasko")).thenReturn(Optional.of(user));
        boolean actual = serviceToBeTested.userExist("Nasko");
        Assertions.assertTrue(actual);

    }

    @Test
    public void testFindByUsername() {
        UserEntity user = new UserEntity();
        user.setUsername("Nasko").setId(1L);
        Mockito.when(mockUserRepository.findByUsername("Nasko")).thenReturn(Optional.of(user));
        UserEntity actual = serviceToBeTested.findByUsername("Nasko");
        String usernameActual = actual.getUsername();
        Assertions.assertEquals("Nasko", usernameActual);
    }

    @Test
    public void testEmailExist() {
        UserEntity user = new UserEntity();
        user.setEmail("Nasko").setId(1L);
        Mockito.when(mockUserRepository.findByEmail("Nasko")).thenReturn(Optional.of(user));
        boolean actual = serviceToBeTested.emailExist("Nasko");
        Assertions.assertTrue(actual);

    }


    @Test
    public void testChangeRole() {
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserRoleEntity adminUser = new UserRoleEntity();
        adminUser.setRole(UserRole.ADMIN);
        List<UserRoleEntity> roles = List.of(role, adminUser);

        UserEntity user = new UserEntity();
        user.setUsername("Nasko").setPassword("12345")
                .setRoles(List.of(role, adminUser)).setId(1L);
        Mockito.when(mockUserRepository.findByUsername("Nasko")).thenReturn(Optional.of(user));
        Mockito.when(mockRoleService.findByRole(UserRole.USER)).thenReturn(role);
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        serviceToBeTested.changeRole("Nasko", "USER");
        Mockito.verify(mockUserRepository, Mockito.times(1)).findByUsername("Nasko");
        Mockito.verify(mockRoleService, Mockito.times(1)).findByRole(UserRole.USER);
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(user);
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

    @Test
    public void testLoadAndRegister() {
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserRoleEntity adminUser = new UserRoleEntity();
        adminUser.setRole(UserRole.ADMIN);
        List<UserRoleEntity> roles = List.of(role, adminUser);

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Nasko").setPassword("12345");
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("Nasko").setPassword("12345").setConfirmPassword("12345").setEmail("ivan0@mail.bg")
                .setFullName("Nasko Ivanov");
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                .collect(Collectors.toList());
        UserDetails principal = new User(
                user.getUsername(),
                user.getPassword(),
                authorities);
        Mockito.when(MockhomerDBUserService.loadUserByUsername("Nasko")).thenReturn(principal);
        Mockito.when(mapper.map(userServiceModel, UserEntity.class)).thenReturn(user);
        Mockito.when(mockRoleService.findByRole(UserRole.USER)).thenReturn(role);
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        serviceToBeTested.registerAndLogin(userServiceModel);
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(user);
    }
}
