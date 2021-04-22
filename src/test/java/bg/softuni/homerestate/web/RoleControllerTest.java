package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.repositories.UserRepository;
import bg.softuni.homerestate.repositories.UserRoleRepository;
import bg.softuni.homerestate.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository repository2;

    @Autowired
    private UserRoleRepository repository4;


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
        UserEntity author2 = new UserEntity();
        author2.setUsername("pesho").setFullName("peshov").setPassword("eeeeee")
                .setRoles(List.of(role)).setEmail("peshoEmail");
        repository2.save(author);
        repository2.save(author2);
    }

    @AfterEach
    public void tearDown(){
        repository2.deleteAll();
        repository4.deleteAll();
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testAdd() throws Exception {
        mockMvc.perform(get("/roles/add")).andExpect(status().isOk())
                .andExpect(model().attributeExists("names","noRole","noUser"))
                        .andExpect(view().name("role-config"));
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testRoleAddConfirm() throws Exception {
        mockMvc.perform(post("/roles/add")
                .param("username", "pesho")
                .param("role", String.valueOf(UserRole.ADMIN))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity user = repository2.findByUsername("pesho").get();
        int roles = user.getRoles().size();
        Assertions.assertEquals(2, roles);

    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testRoleErrorConfirm() throws Exception {
        mockMvc.perform(post("/roles/add")
                .param("username", "")
                .param("role", "null")
                .with(csrf()))
                .andExpect(flash().attributeExists("model"))
                .andExpect(flash().attributeExists("noUser"))
                .andExpect(status().is3xxRedirection());
        UserEntity user = repository2.findByUsername("pesho").get();
        int roles = user.getRoles().size();
        Assertions.assertEquals(1, roles);

    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testRoleError2Confirm() throws Exception {
        mockMvc.perform(post("/roles/add")
                .param("username", "")
                .with(csrf()))
                .andExpect(flash().attributeExists("model"))
                .andExpect(flash().attributeExists("noUser"))
                .andExpect(status().is3xxRedirection());
        UserEntity user = repository2.findByUsername("pesho").get();
        int roles = user.getRoles().size();
        Assertions.assertEquals(1, roles);

    }
}
