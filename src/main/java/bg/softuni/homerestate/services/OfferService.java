package bg.softuni.homerestate.services;

import bg.softuni.homerestate.models.binding.SearchBindingModel;
import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.service.OfferServiceModel;
import bg.softuni.homerestate.models.view.OfferDetailsViewModel;
import bg.softuni.homerestate.models.view.OfferViewModel;

import java.io.IOException;
import java.util.List;

public interface OfferService {
    void saveOffer(OfferServiceModel model) throws IOException;
    List<OfferViewModel> loadViewModels();
    OfferDetailsViewModel getOffer(Long id);
    List<OfferViewModel> findAllByType(Type type);
    Offer getOfferEntity(Long id);
    void deleteOffer(Long id);
    List<OfferViewModel> findAll();
    List<OfferViewModel>customSearch(SearchBindingModel model);
    void editOffer(OfferServiceModel serviceModel, Long id);
    List<OfferViewModel> getOfferByAuthor();
}
