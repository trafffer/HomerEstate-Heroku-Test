package bg.softuni.homerestate.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class SaleControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testAllSales() throws Exception {
        mockMvc.perform(get("/sales/all")).andExpect(status().isOk())
                .andExpect(view().name("sale"))
                .andExpect(model().attributeExists("sales"));
    }
}
