package bg.softuni.homerestate.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;



    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testLogin() throws Exception {
        mockMvc.perform(get("/users/login")).andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testRegister() throws Exception {
        mockMvc.perform(get("/users/register")).andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

}
