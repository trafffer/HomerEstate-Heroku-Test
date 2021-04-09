package bg.softuni.homerestate.web;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testIndex() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("offers","categories","cities","isHome"));
    }

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testAbout() throws Exception {
        mockMvc.perform(get("/about")) .andExpect(view().name("about-us"));
    }

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testContacts() throws Exception {
        mockMvc.perform(get("/contact")).andExpect(view().name("contacts"));
    }
    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testAllOffers() throws Exception {
        mockMvc.perform(get("/all")).andExpect(view().name("search"))
                .andExpect(model().attributeExists("customSearch"));
    }


    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testSearch() throws Exception {
        mockMvc.perform(get("/search")).andExpect(view().name("search"))
                .andExpect(model().attributeExists("customSearch"));
    }
}
