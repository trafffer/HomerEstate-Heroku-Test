package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.services.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RentControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testAllRents() throws Exception {
        mockMvc.perform(get("/rents/all")).andExpect(status().isOk())
                .andExpect(view().name("rent"))
                .andExpect(model().attributeExists("rents"));
    }

}
