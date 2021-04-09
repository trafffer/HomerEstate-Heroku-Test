package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.CategoryEntity;
import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.repositories.CategoryRepository;
import bg.softuni.homerestate.repositories.OfferRepository;
import bg.softuni.homerestate.repositories.UserRepository;
import bg.softuni.homerestate.repositories.UserRoleRepository;
import bg.softuni.homerestate.services.OfferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository repository;

    @Autowired
    private UserRepository repository2;

    @Autowired
    private CategoryRepository repository3;

    @Autowired
    private UserRoleRepository repository4;

    @BeforeEach
    public void setUp(){
        repository.deleteAll();
        repository2.deleteAll();
        repository3.deleteAll();
        repository4.deleteAll();
        UserEntity author = new UserEntity();
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        repository4.save(role);
        author.setUsername("nasko").setFullName("atanas").setPassword("eeeeee")
                .setRoles(List.of(role)).setEmail("jfhdifdjfid");
        repository2.save(author);
        CategoryEntity category = new CategoryEntity().setName(Category.HOUSE);
        repository3.save(category);
        Offer offer = new Offer();
        offer.setCategory(category).setFloor(1).setRooms(2).setDescription("ttttt")
                .setArea(144).setCity(City.BLAGOEVGRAD).setType(Type.SALE)
                .setImgUrl("this-image").setCreatedOn(LocalDateTime.now())
                .setVisited(0)
                .setPrice(BigDecimal.valueOf(144000)).setAuthor(author)
                .setAddress("address45").setPricePerSqM(BigDecimal.valueOf(1445.55)).setId(0L);
        Offer offer2 = repository.save(offer);
    }

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
        repository2.deleteAll();
        repository3.deleteAll();
        repository4.deleteAll();
    }


    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/currency/api"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testNotFound() throws Exception {
        mockMvc.perform(get("/currency/apitttt"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testBody() throws Exception {
        mockMvc.perform(get("/currency/api"))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$.[0].address",is("address45")));

    }
}
