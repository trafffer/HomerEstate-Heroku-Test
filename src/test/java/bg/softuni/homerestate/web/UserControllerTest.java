package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.*;
import bg.softuni.homerestate.models.entities.enums.*;
import bg.softuni.homerestate.repositories.CategoryRepository;
import bg.softuni.homerestate.repositories.OfferRepository;
import bg.softuni.homerestate.repositories.UserRepository;
import bg.softuni.homerestate.repositories.UserRoleRepository;
import bg.softuni.homerestate.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository repository2;

    @Autowired
    private UserRoleRepository repository4;

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testLogin() throws Exception {
        mockMvc.perform(get("/users/login")).andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @BeforeEach
    public void setUp(){
        repository2.deleteAll();
        repository4.deleteAll();
        UserEntity author = new UserEntity();
        UserRoleEntity role = new UserRoleEntity();
        UserRoleEntity role1 = new UserRoleEntity();
        role.setRole(UserRole.USER);
        role1.setRole(UserRole.ADMIN);
        repository4.save(role);
        repository4.save(role1);
        author.setUsername("nasko").setFullName("atanas").setPassword("eeeeee")
                .setRoles(List.of(role,role1)).setEmail("jfhdifdjfid");
        repository2.save(author);
    }

    @AfterEach
    public void tearDown(){
        repository2.deleteAll();
        repository4.deleteAll();
    }

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testRegister() throws Exception {
        mockMvc.perform(get("/users/register")).andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testEditProfile() throws Exception {
        mockMvc.perform(get("/users/edit")).andExpect(status().isOk())
                .andExpect(view().name("edit-profile"))
                .andExpect(model().attributeExists("userViewModel","userBindingModel",
                        "userExist","emailExist"));
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testRegisterConfirm() throws Exception {
        mockMvc.perform(post("/users/register")
                .param("username", "Ivan")
                .param("fullName", "Milin")
                .param("password", "0895777719")
                .param("confirmPassword", "0895777719")
                .param("email", "ivan0@mail.bg")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(2, repository2.count());

    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testRegisterConfirmError() throws Exception {
        mockMvc.perform(post("/users/register")
                .param("username", "I")
                .param("fullName", "Milin")
                .param("password", "0895777719")
                .param("confirmPassword", "0895777719")
                .param("email", "ivan0@mail.bg")
                .with(csrf()))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.userModel"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, repository2.count());

    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testEditConfirm() throws Exception {
        mockMvc.perform(post("/users/edit")
                .param("username", "Ivan")
                .param("fullName", "Milin")
                .param("password", "0895777719")
                .param("confirmPassword", "0895777719")
                .param("email", "ivan0@mail.bg")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, repository2.count());
        UserEntity userEntity = userService.findByUsername("Ivan");
        Assertions.assertEquals("Milin",userEntity.getFullName());
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testEditConfirmError() throws Exception {
        mockMvc.perform(post("/users/edit")
                .param("username", "I")
                .param("fullName", "Milin")
                .param("password", "0895777719")
                .param("confirmPassword", "0895777719")
                .param("email", "ivan0@mail.bg")
                .with(csrf()))
                .andExpect(flash()
                        .attributeExists("org.springframework.validation.BindingResult.userBindingModel"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, repository2.count());
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testLoginError() throws Exception {
        mockMvc.perform(post("/users/login-error")
                .with(csrf()))
                .andExpect(flash().attributeExists("bad_credentials"))
                .andExpect(flash().attributeExists("username"))
                .andExpect(status().is3xxRedirection());
    }
}
