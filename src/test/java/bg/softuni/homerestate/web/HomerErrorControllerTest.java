package bg.softuni.homerestate.web;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomerErrorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    


    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testaccessDenied() throws Exception {
        mockMvc.perform(get("/error-403")).andExpect(status().isOk())
                .andExpect(view().name("error-403"))
                .andExpect(model().attributeExists("message"));
    }
}
