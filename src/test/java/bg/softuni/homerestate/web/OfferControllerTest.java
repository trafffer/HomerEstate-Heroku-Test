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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OfferService offerService;

    @Autowired
    ModelMapper modelMapper;


    @Autowired
    private OfferRepository repository;

    @Autowired
    private UserRepository repository2;

    @Autowired
    private CategoryRepository repository3;

    @Autowired
    private UserRoleRepository repository4;


    @BeforeEach
    public void setUp() {
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
                .setRoles(List.of(role, role1)).setEmail("jfhdifdjfid");
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
    }

    @AfterEach
    public void tearDown() {

        repository.deleteAll();
        repository2.deleteAll();
        repository3.deleteAll();
        repository4.deleteAll();
    }

    @Test
    @WithMockUser(username = "nasko", roles = {"USER", "ADMIN"})
    public void testAddOffer() throws Exception {
        mockMvc.perform(get("/offers/add")).andExpect(status().isOk())
                .andExpect(view().name("add-offer"))
                .andExpect(model()
                        .attributeExists("offerModel", "noPictures",
                                "noType", "categories", "cities"));
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testAddOfferConfirm() throws Exception {
        Path path = Paths.get("/path/to/the/file.txt");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException ignored) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/offers/add")
                .file("files",result.getBytes())
                .param("type", String.valueOf(Type.RENT))
                .param("category", String.valueOf(Category.HOUSE))
                .param("price", "15000")
                .param("city", String.valueOf(City.BLAGOEVGRAD))
                .param("address","nekade na krushata")
                .param("area", "125")
                .param("floor", String.valueOf(12))
                .param("rooms", String.valueOf(4))
                .param("description","This is a test description")
                .param("imgUrl","this is an imgUrl")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1,repository.count());
    }

    @Test
    @WithMockUser(username = "nasko", roles = {"USER", "ADMIN"})
    public void testEditOffer() throws Exception {
        Long id = repository.findAll().stream().findFirst().get().getId();
        mockMvc.perform(get("/offers/edit/"+id)).andExpect(status().isOk())
                .andExpect(view().name("edit-offer"))
                .andExpect(model()
                        .attributeExists("viewModel","offerModel", "noPictures",
                                "noType", "categories", "cities"));
    }

    @Test
    @WithMockUser(username = "nasko",roles = {"USER","ADMIN"})
    public void testEditOfferConfirm() throws Exception {
        Path path = Paths.get("/path/to/the/file.txt");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException ignored) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        Long id = repository.findAll().stream().findFirst().get().getId();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/offers/edit/"+id)
                .file("files",result.getBytes())
                .param("type", String.valueOf(Type.RENT))
                .param("category", String.valueOf(Category.HOUSE))
                .param("price", "15000")
                .param("city", String.valueOf(City.BLAGOEVGRAD))
                .param("address","nekade na krushata")
                .param("area", "125")
                .param("floor", String.valueOf(12))
                .param("rooms", String.valueOf(4))
                .param("description","This is a test description")
                .param("imgUrl","this is an imgUrl")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1,repository.count());
    }
}








