package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.binding.SearchBindingModel;
import bg.softuni.homerestate.models.entities.CategoryEntity;
import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.service.OfferServiceModel;
import bg.softuni.homerestate.models.view.OfferDetailsViewModel;
import bg.softuni.homerestate.models.view.OfferViewModel;
import bg.softuni.homerestate.repositories.OfferRepository;
import bg.softuni.homerestate.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final ModelMapper mapper;
    private final OfferRepository offerRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public OfferServiceImpl(ModelMapper mapper,
                            OfferRepository offerRepository,
                            CategoryService categoryService,
                            UserService userService) {
        this.mapper = mapper;
        this.offerRepository = offerRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @Override
    public void saveOffer(OfferServiceModel model) {
        Offer offer = mapper.map(model,Offer.class);
        offer.setCategory(getCategory(model.getCategory()));
        offer.setCreatedOn(LocalDateTime.now());
        offer.setAuthor(getUser());
        offer.setVisited(0);
        offer.setImgUrl(String.join(",", offer.getImgUrl()));
        offer.setPricePerSqM(getPricePerArea(model));
        offerRepository.save(offer);
    }

    private BigDecimal getPricePerArea(OfferServiceModel model) {
        return model.getPrice().divide(BigDecimal.valueOf(model.getArea()), RoundingMode.CEILING);
    }

    @Override
    public List<OfferViewModel> loadViewModels() {
        long max;
        if (offerRepository.count()>9){
            max=9;
        }else{
            max=offerRepository.count();
        }
        List<Offer> offers = offerRepository.findAllOrderByVisitedDesc().subList(0,(int)max);
        List<OfferServiceModel> models = collectOfferServiceModels(offers);
        return mapToOfferViewModel(models);
    }

    @Override
    public OfferDetailsViewModel getOffer(Long id) {
       Offer offer =  getOfferEntity(id);
       visitOffer(offer);
       OfferDetailsViewModel viewModel = mapper.map(offer,OfferDetailsViewModel.class);
       viewModel.setId(id);
       viewModel.setCategory(offer.getCategory().getName());
       viewModel.setImgUrl(parseImgUrls(offer));
        return viewModel;
    }

    @Override
    public List<OfferViewModel> findAllByType(Type type) {
        List<Offer>offers = offerRepository.findAllByTypeOrderByVisitedDesc(type);
        List<OfferServiceModel> models = collectOfferServiceModels(offers);
        return mapToOfferViewModel(models);
    }

    @Override
    public Offer getOfferEntity(Long id) {
        return offerRepository
                .findById(id).orElseThrow(()->new IllegalArgumentException("There is no such offer"));
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public List<OfferViewModel> findAll() {
        List<Offer>offers =  offerRepository.findAll();
        List<OfferServiceModel> models = collectOfferServiceModels(offers);
        return mapToOfferViewModel(models);
    }

    @Override
    public List<OfferViewModel> customSearch(SearchBindingModel model) {
       List<Offer>offers =  offerRepository.searchOffersByCriteria(model.getType(),
                model.getCategory(),
                model.getCity(),
                model.getPriceLow(),
                model.getPriceHigh());
       List<OfferServiceModel> models = collectOfferServiceModels(offers);
       return mapToOfferViewModel(models);
    }

    @Override
    public void editOffer(OfferServiceModel serviceModel, Long id) {
        Offer offer = getOfferEntity(id);
        offer.setType(serviceModel.getType())
                .setCategory(getCategory(serviceModel.getCategory()))
                .setPrice(serviceModel.getPrice())
                .setCity(serviceModel.getCity())
                .setAddress(serviceModel.getAddress())
                .setArea(serviceModel.getArea())
                .setFloor(serviceModel.getFloor())
                .setRooms(serviceModel.getRooms())
                .setDescription(serviceModel.getDescription())
                .setImgUrl(serviceModel.getImgUrl().toString())
                .setPricePerSqM(getPricePerArea(serviceModel));
        offerRepository.save(offer);
    }

    @Override
    public List<OfferViewModel> getOfferByAuthor() {
        List<Offer> offers = offerRepository.findAllByAuthorOrderByVisitedDesc(userService.getUser());
        List<OfferServiceModel> serviceModels = collectOfferServiceModels(offers);
        return mapToOfferViewModel(serviceModels);
    }

    private List<OfferServiceModel> collectOfferServiceModels(List<Offer>models) {
        return models.stream().map(o -> {
            OfferServiceModel model = mapper.map(o, OfferServiceModel.class);
            model.setType(o.getType());
            model.setCity(o.getCity());
            model.setCategory(o.getCategory().getName());
            model.setImgUrl(parseImgUrls(o));
            return model;
        }).collect(Collectors.toList());
    }

    private List<OfferViewModel>mapToOfferViewModel(List<OfferServiceModel>models){
        return models.stream().map(o -> {
            OfferViewModel model = mapper.map(o, OfferViewModel.class);
            model.setType(o.getType().getName());
            model.setCity(o.getCity().getName());
            model.setCategory(o.getCategory().getName());
            model.setImgUrl(o.getImgUrl().get(0));
            return model;
        }).collect(Collectors.toList());
    }

    private CategoryEntity getCategory(Category category){
        return categoryService.findByCategoryName(category);
    }

    private UserEntity getUser(){
        return userService.getUser();
    }

    private void visitOffer(Offer offer){
        offer.incrementVisited();
        offerRepository.save(offer);
    }
    private List<String> parseImgUrls(Offer offer){
        String img = offer.getImgUrl().replaceAll("(\\[|\\])","");
       return Arrays.stream(img.split(", ")).collect(Collectors.toList());
    }
}
