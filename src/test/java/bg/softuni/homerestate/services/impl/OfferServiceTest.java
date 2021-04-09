package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.CategoryEntity;
import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.models.service.OfferServiceModel;
import bg.softuni.homerestate.repositories.OfferRepository;
import bg.softuni.homerestate.services.CategoryService;
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
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {
    private OfferServiceImpl serviceForTest;


    @Mock
    ModelMapper mapper;
    @Mock
    OfferRepository mockOfferRepository;
    @Mock
    CategoryService mockCategoryService;
    @Mock
    UserService mockUserService;

    @BeforeEach
    public void setUp() {
        serviceForTest =
                new OfferServiceImpl(mapper,mockOfferRepository,mockCategoryService,mockUserService);
    }


    @Test
    public void testSaveOffer(){
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
                .setRooms(5).setDescription("ddddd").setId(0L);
        OfferServiceModel testModel = new OfferServiceModel();
        testModel.setArea(144).setAuthor(author).setCity(City.BLAGOEVGRAD)
                .setCategory(category.getName())
                .setCreatedOn(LocalDateTime.now()).setFloor(1).setId(0L).setDescription("ddddd")
                .setPrice(BigDecimal.valueOf(1444444)).setPricePerSqM(BigDecimal.valueOf(14440))
                .setRooms(5).setType(Type.SALE).setVisited(0);
        testModel.setAddress("thisForTest");
        Mockito.when(mapper.map(testModel, Offer.class)).thenReturn(offer);
        Mockito.when(mockOfferRepository.save(offer)).thenReturn(new Offer());
        serviceForTest.saveOffer(testModel);
        Mockito.verify(mapper,Mockito.times(1)).map(testModel,Offer.class);
        Mockito.verify(mockOfferRepository,Mockito.times(1)).save(offer);
    }
}
