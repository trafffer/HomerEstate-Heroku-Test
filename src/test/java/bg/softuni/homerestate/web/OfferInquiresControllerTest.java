package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.*;
import bg.softuni.homerestate.models.entities.enums.*;
import bg.softuni.homerestate.models.view.InquiryViewModel;
import bg.softuni.homerestate.repositories.*;
import bg.softuni.homerestate.services.InquiryService;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferInquiresControllerTest {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository repository;

    @Autowired
    private UserRepository repository2;

    @Autowired
    private CategoryRepository repository3;

    @Autowired
    private UserRoleRepository repository4;

    @Autowired
    private InquiryRepository repository5;

    @BeforeEach
    public void setUp(){
        repository5.deleteAll();
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
        offer.setId(1L);
                offer.setCategory(category).setFloor(1).setRooms(2).setDescription("ttttt")
                .setArea(144).setCity(City.BLAGOEVGRAD).setType(Type.SALE)
                .setImgUrl("this-image").setCreatedOn(LocalDateTime.now())
                .setVisited(0)
                .setPrice(BigDecimal.valueOf(144000)).setAuthor(author)
                .setAddress("address45").setPricePerSqM(BigDecimal.valueOf(1445.55));
        Offer offer1= repository.save(offer);
        Inquiry inquiry= new Inquiry();
        inquiry.setCreatedOn(LocalDateTime.now()).setContactHour(ContactHours.EVENING)
                .setOffer(offer1).setDescription("here").setEmail("email").setFirstName("nasko")
                .setLastName("gosho").setPhoneNumber("089577719");
        repository5.save(inquiry);
    }

    @AfterEach
    public void tearDown(){
        repository5.deleteAll();
        repository.deleteAll();
        repository2.deleteAll();
        repository3.deleteAll();
        repository4.deleteAll();
    }

    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/details/all/1"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testNotFound() throws Exception {
        mockMvc.perform(get("/details/all/4"))
                .andExpect(jsonPath("$",hasSize(0)));
    }
    @Test
    @WithMockUser(username = "pesho",roles = {"USER","ADMIN"})
    public void testBody() throws Exception {
        Long id = repository.findAll().stream().findFirst().get().getId();
        mockMvc.perform(get("/details/all/"+id))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$.[0].firstName",is("nasko")));

    }



}
