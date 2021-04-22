package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.*;
import bg.softuni.homerestate.models.entities.enums.*;
import bg.softuni.homerestate.models.service.InquiryServiceModel;
import bg.softuni.homerestate.models.view.InquiryViewModel;
import bg.softuni.homerestate.repositories.InquiryRepository;
import bg.softuni.homerestate.services.OfferService;
import org.junit.Assert;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class InquiryServiceTest {
    private InquiryServiceImpl serviceToBeTested;

    @Mock
    ModelMapper mapper;

    @Mock
    InquiryRepository mockRepository;

    @Mock
    OfferService mockOfferService;


    @BeforeEach
    public void setUp() {
        serviceToBeTested =
                new InquiryServiceImpl(mockRepository, mapper, mockOfferService);

    }


    @Test
    public void saveInquiryTest(){
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
        Inquiry inquiry= new Inquiry();
        inquiry.setCreatedOn(LocalDateTime.now());
        inquiry.setOffer(offer);
        InquiryServiceModel inquiryServiceModel = new InquiryServiceModel();
        inquiryServiceModel.setCreatedOn(LocalDateTime.now());
        inquiryServiceModel.setOffer(offer);
        Mockito.when(mockOfferService.getOfferEntity(0L)).thenReturn(offer);
        Mockito.when(mapper.map(inquiryServiceModel, Inquiry.class)).thenReturn(inquiry);
        serviceToBeTested.saveInquiry(inquiryServiceModel,0L);
        Mockito.verify(mockRepository,Mockito.times(1)).save(inquiry);
    }

    @Test
    public void loadViewModelsTest(){
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
        Inquiry inquiry= new Inquiry();
        inquiry.setCreatedOn(LocalDateTime.now()).setContactHour(ContactHours.EVENING)
                .setOffer(offer).setDescription("here").setEmail("email").setFirstName("nasko")
                .setLastName("gosho").setPhoneNumber("089577719");
        List<Inquiry>inquiries = List.of(inquiry);
        InquiryServiceModel inquiryServiceModel = new InquiryServiceModel();
        inquiryServiceModel.setCreatedOn(LocalDateTime.now());
        inquiryServiceModel.setOffer(offer);
        InquiryViewModel inquiryViewModel = new InquiryViewModel();
        inquiryViewModel.setCreatedOn("now").setFirstName("nasko").setLastName("gosho")
        .setEmail("mail").setDescription("here");

        Mockito.when(mockRepository.findAllByOfferIdOrderByCreatedOnDesc(offer.getId()))
                .thenReturn(inquiries);
        Mockito.when(mapper.map(inquiry, InquiryViewModel.class))
                .thenReturn(inquiryViewModel);
        List<InquiryViewModel>inquirieses = serviceToBeTested.loadViewModels(offer.getId());
        InquiryViewModel actualModel = inquirieses.get(0);
        Assertions.assertEquals("here",actualModel.getDescription());
    }

    @Test
    public void deleteByOfferIdTest(){
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
        Inquiry inquiry= new Inquiry();
        inquiry.setCreatedOn(LocalDateTime.now()).setContactHour(ContactHours.EVENING)
                .setOffer(offer).setDescription("here").setEmail("email").setFirstName("nasko")
                .setLastName("gosho").setPhoneNumber("089577719");
        List<Inquiry>inquiries = List.of(inquiry);
        Mockito.when(mockRepository.findAllByOfferIdOrderByCreatedOnDesc(0L))
                .thenReturn(inquiries);
        Long id = inquiries.stream().findFirst().get().getId();
        serviceToBeTested.deleteByOfferId(0L);
        Mockito
                .verify(mockRepository,Mockito.times(1))
                .findAllByOfferIdOrderByCreatedOnDesc(0L);
        Mockito.verify(mockRepository,Mockito.times(1))
                .deleteById(id);
    }

}
