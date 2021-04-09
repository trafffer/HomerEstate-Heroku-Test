package bg.softuni.homerestate.web;

import bg.softuni.homerestate.models.binding.CommentAddBindingModel;
import bg.softuni.homerestate.models.entities.*;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.models.service.CommentServiceModel;
import bg.softuni.homerestate.repositories.*;
import bg.softuni.homerestate.services.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CommentService service;

    @Autowired
    UserRepository repository2;

    @Autowired
    CategoryRepository repository3;

    @Autowired
    UserRoleRepository repository4;

    @Autowired
    OfferRepository repository;

    @Autowired
    CommentRepository repository5;

    @Autowired
    ModelMapper mapper;


    @BeforeEach
    public void setUp() {
        repository5.deleteAll();
        repository.deleteAll();
        repository2.deleteAll();
        repository4.deleteAll();
        repository3.deleteAll();
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        repository4.save(role);
        UserEntity author = new UserEntity();
        author.setUsername("Nasko");
        author.setRoles(List.of(role)).setPassword("pppp")
                .setEmail("koko@mail.bg").setFullName("Atanas");
        repository2.save(author);
        CategoryEntity category = new CategoryEntity();
        category.setName(Category.HOUSE);
        repository3.save(category);
        Offer offer = new Offer();
        offer.setAuthor(author).setAddress("ffffff").setPrice(BigDecimal.valueOf(1800.00))
                .setCreatedOn(LocalDateTime.now()).setImgUrl("iiiiii").setCategory(category)
                .setType(Type.SALE).setCity(City.BLAGOEVGRAD).setArea(144).setFloor(1)
                .setRooms(5).setPricePerSqM(BigDecimal.valueOf(1300)).setDescription("ddddddddd")
                .setVisited(0).setCreatedOn(LocalDateTime.now());
        repository.save(offer);
        OfferComment comment = new OfferComment();
        comment.setCreatedOn(LocalDateTime.now()).setAuthor(author);
        comment.setOffer(offer).setTextContent("text3").setTimeForVisit(LocalDateTime.now());
        repository5.save(comment);
        OfferComment comment2 = new OfferComment();
        comment2.setCreatedOn(LocalDateTime.now()).setAuthor(author)
                .setOffer(offer).setTextContent("newText").setTimeForVisit(LocalDateTime.now());
        repository5.save(comment2);

    }

    @AfterEach
    public void tearDown() {
        repository5.deleteAll();
        repository.deleteAll();
        repository2.deleteAll();
        repository4.deleteAll();
        repository3.deleteAll();
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER", "ADMIN"})
    public void testSearch() throws Exception {
        Long id = repository.findAll().stream().findFirst().get().getId();
        mockMvc.perform(get("/comments/search/{id}",id))
                .andExpect(status().isOk())
                .andExpect(view().name("details-offer"))
                .andExpect(model().attributeExists("commentsList", "offerModel","comment"));
    }

    @Test
    @WithMockUser(username = "pesho", roles = {"USER", "ADMIN"})
    public void testDeleteOffer() throws Exception {
        Long id = repository.findAll().stream().findFirst().get().getId();
        mockMvc.perform(get("/comments/delete/{id}",id))
                .andExpect(redirectedUrl("/"));
    }
}
