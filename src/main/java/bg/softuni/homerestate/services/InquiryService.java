package bg.softuni.homerestate.services;

import bg.softuni.homerestate.models.service.InquiryServiceModel;
import bg.softuni.homerestate.models.view.InquiryViewModel;

import java.util.List;

public interface InquiryService {
    void saveInquiry(InquiryServiceModel inquiryServiceModel,Long id);
    List<InquiryViewModel> loadViewModels(Long id);
    void deleteByOfferId(Long id);
}
