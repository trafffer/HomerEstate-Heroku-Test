package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.entities.*;
import bg.softuni.homerestate.models.entities.enums.*;
import bg.softuni.homerestate.repositories.*;
import bg.softuni.homerestate.services.InquiryService;
import bg.softuni.homerestate.services.OfferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class InquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OfferService offerService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    InquiryService inquiryService;

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
        UserRoleEntity role1 = new UserRoleEntity();
        role.setRole(UserRole.USER);
        role1.setRole(UserRole.ADMIN);
        repository4.save(role);
        repository4.save(role1);
        author.setUsername("nasko").setFullName("atanas").setPassword("eeeeee")
                .setRoles(List.of(role,role1)).setEmail("jfhdifdjfid");
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
        Offer offer1 = repository.save(offer);
        Inquiry inquiry= new Inquiry();
        inquiry.setCreatedOn(LocalDateTime.now()).setContactHour(ContactHours.EVENING)
                .setOffer(offer1).setDescription("hereeee").setEmail("email")
                .setFirstName("nasko")
                .setLastName("gosho")
                .setPhoneNumber("089577719");
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
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testAddInquiry() throws Exception {
        mockMvc.perform(get("/inquiries/add")).andExpect(status().isOk())
                .andExpect(view().name("inquiries"))
                .andExpect(model().attributeExists("offers"));
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testAddInquiryConfirm() throws Exception {
        Long id = repository.findAll().stream().findFirst().get().getId();
        mockMvc.perform(post("/inquiries/add/"+id)
                .param("firstName","Ivan")
                .param("lastName","Milin")
                .param("phoneNumber","0895777719")
                .param("email","ivan0@mail.bg")
                .param("contactHour", String.valueOf(ContactHours.MORNING))
                .param("description","This is a test description")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(2,repository5.count());
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testAddInquiryWrong() throws Exception {
        Long id = repository.findAll().stream().findFirst().get().getId();
        mockMvc.perform(post("/inquiries/add/"+id)
                .param("firstName","Iv")
                .param("lastName","Mi")
                .param("phoneNumber","aaaaa")
                .param("email","ivan0@mail.bg")
                .param("contactHour", String.valueOf(ContactHours.MORNING))
                .param("description","This is a test description")
                .with(csrf()))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.inquiryModel"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1,repository5.count());
    }
}

