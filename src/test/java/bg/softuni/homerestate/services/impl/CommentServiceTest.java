package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.*;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.models.service.CommentServiceModel;
import bg.softuni.homerestate.models.view.CommentViewModel;
import bg.softuni.homerestate.models.view.OfferDetailsViewModel;
import bg.softuni.homerestate.models.view.OfferViewModel;
import bg.softuni.homerestate.repositories.CommentRepository;
import bg.softuni.homerestate.services.OfferService;
import bg.softuni.homerestate.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    private CommentServiceImpl serviceToBeTested;


    @Mock
    CommentRepository mockCommentRepository;
    @Mock
    OfferService mockOfferService;
    @Mock
    UserService mockUserService;
    @Mock
    ModelMapper mapper;

    @BeforeEach
    public void setUp() {
        serviceToBeTested =
                new CommentServiceImpl(mockCommentRepository, mapper, mockOfferService, mockUserService);
    }

    @Test
    public void testListAllComments() {
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserEntity author = new UserEntity();
        author.setUsername("Nasko");
        author.setRoles(List.of(role)).setPassword("pppp")
                .setEmail("koko@mail.bg").setFullName("Atanas").setId(0L);
        CategoryEntity category = new CategoryEntity();
        category.setName(Category.HOUSE);
        Offer offer = new Offer();
        offer.setAuthor(author).setAddress("ffffff").setPrice(BigDecimal.valueOf(1800.00))
                .setCreatedOn(LocalDateTime.now()).setImgUrl("iiiiii").setCategory(category)
                .setType(Type.SALE).setCity(City.BLAGOEVGRAD).setArea(144).setFloor(1)
                .setRooms(5).setDescription("ddddddddd").setId(0L);

        OfferComment comment = new OfferComment();
        comment.setCreatedOn(LocalDateTime.now()).setAuthor(author);
        comment.setOffer(offer).setTextContent("text");
        OfferComment comment2 = new OfferComment();
        comment2.setCreatedOn(LocalDateTime.now()).setAuthor(author);
        comment2.setOffer(offer).setTextContent("text2");
        List<OfferComment> list = List.of(comment, comment2);
        Mockito.when(mockCommentRepository.findAll()).thenReturn(List.copyOf(list));
        List<OfferComment> tested = serviceToBeTested.listAllComments();
        String testedContent = tested.get(1).getTextContent();
        Assertions.assertEquals("text2", testedContent);
        Assertions.assertEquals(list.size(), tested.size());

    }

    @Test
    public void testSaveComment() {
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserEntity author = new UserEntity();
        author.setUsername("Nasko");
        author.setRoles(List.of(role)).setPassword("pppp")
                .setEmail("koko@mail.bg").setFullName("Atanas").setId(0L);
        CategoryEntity category = new CategoryEntity();
        category.setName(Category.HOUSE);
        Offer offer = new Offer();
        offer.setAuthor(author).setAddress("ffffff").setPrice(BigDecimal.valueOf(1800.00))
                .setCreatedOn(LocalDateTime.now()).setImgUrl("iiiiii").setCategory(category)
                .setType(Type.SALE).setCity(City.BLAGOEVGRAD).setArea(144).setFloor(1)
                .setRooms(5).setDescription("ddddddddd").setId(0L);
        OfferComment comment = new OfferComment();
        comment.setCreatedOn(LocalDateTime.now()).setAuthor(author);
        comment.setOffer(offer).setTextContent("text3");

        Mockito.when(mockOfferService.getOfferEntity(0L)).thenReturn(offer);
        Mockito.when(mockUserService.getUser()).thenReturn(author);

        CommentServiceModel serviceModel = new CommentServiceModel();
        serviceModel.setAuthor(author).setOffer(offer)
                .setCreatedOn(LocalDateTime.now()).setTextContent("text4");

        Mockito.when(mockCommentRepository.save(any(OfferComment.class))).thenReturn(comment);
        Mockito.when(mapper.map(serviceModel, OfferComment.class)).thenReturn(comment);
        serviceToBeTested.saveComment(serviceModel, 0L);
        Mockito.verify(mockCommentRepository, times(1)).save(any(OfferComment.class));
    }


    @Test
    public void testFindAllByOfferId(){
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserEntity author = new UserEntity();
        author.setUsername("Nasko");
        author.setRoles(List.of(role)).setPassword("pppp")
                .setEmail("koko@mail.bg").setFullName("Atanas").setId(0L);
        CategoryEntity category = new CategoryEntity();
        category.setName(Category.HOUSE);
        Offer offer = new Offer();
        offer.setAuthor(author).setAddress("ffffff").setPrice(BigDecimal.valueOf(1800.00))
                .setCreatedOn(LocalDateTime.now()).setImgUrl("iiiiii").setCategory(category)
                .setType(Type.SALE).setCity(City.BLAGOEVGRAD).setArea(144).setFloor(1)
                .setRooms(5).setDescription("ddddddddd").setId(0L);

        OfferComment comment = new OfferComment();
        comment.setCreatedOn(LocalDateTime.now()).setAuthor(author);
        comment.setOffer(offer).setTextContent("text4").setTimeForVisit(LocalDateTime.now());

        CommentViewModel serviceModel = new CommentViewModel();
        serviceModel.setAuthorUsername("nasko").setOfferId(0L)
                .setCreatedOn(LocalDateTime.now().toString()).setTextContent("text4");

        List<OfferComment>list = new ArrayList<>();
        list.add(comment);
        Mockito.when(mockCommentRepository.findAllByOfferId(0L)).thenReturn(list);
        Mockito.when(mapper.map(comment, CommentViewModel.class)).thenReturn(serviceModel);

        List<CommentViewModel>actual = serviceToBeTested.findAllByOfferId(0L);
        String actualText = actual.get(0).getTextContent();
        Assertions.assertEquals("text4",actualText);
    }

    @Test
    public void testDeleteById(){
        serviceToBeTested.deleteById(0L);
        Mockito.verify(mockCommentRepository, times(1)).deleteByOfferId(0L);
        Mockito.verify(mockOfferService,times(1)).deleteOffer(0L);
    }

    @Test
    public void testSheduledDelete() throws InterruptedException {
        Offer offer = new Offer();
                offer.setId(1L);
        OfferComment comment = new OfferComment();
        comment.setOffer(offer)
                .setTimeForVisit(LocalDateTime.of(2020,5,22,13,14,5))
                .setTextContent("dddd");
        Mockito.when(mockCommentRepository.findAllByTimeForVisitBefore(any()))
                .thenReturn(List.of(comment.setOffer(offer)));
        serviceToBeTested.deleteOldComments();
        Mockito.verify(mockCommentRepository,times(1)).deleteByOfferId(null);
    }

    @Test
    public void testSheduledDeleteEmpty() throws InterruptedException {
        Offer offer = new Offer();
        offer.setId(1L);
        OfferComment comment = new OfferComment();
        comment.setOffer(offer)
                .setTimeForVisit(LocalDateTime.of(2025,5,22,13,14,5))
                .setTextContent("dddd");
        Mockito.when(mockCommentRepository.findAllByTimeForVisitBefore(any()))
                .thenReturn(new ArrayList<>());
        serviceToBeTested.deleteOldComments();
        Mockito.verifyNoInteractions(mockOfferService);

    }
}

