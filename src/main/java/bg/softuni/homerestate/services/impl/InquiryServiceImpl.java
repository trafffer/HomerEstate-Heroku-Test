package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.Inquiry;
import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.service.InquiryServiceModel;
import bg.softuni.homerestate.models.view.InquiryViewModel;
import bg.softuni.homerestate.repositories.InquiryRepository;
import bg.softuni.homerestate.services.InquiryService;
import bg.softuni.homerestate.services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;
    private final ModelMapper mapper;
    private final OfferService offerService;

    public InquiryServiceImpl(InquiryRepository inquiryRepository, ModelMapper mapper, OfferService offerService) {
        this.inquiryRepository = inquiryRepository;
        this.mapper = mapper;
        this.offerService = offerService;
    }

    @Override
    public void saveInquiry(InquiryServiceModel inquiryServiceModel,Long id) {
          Offer offer = offerService.getOfferEntity(id);
          inquiryServiceModel.setOffer(offer);
          inquiryServiceModel.setCreatedOn(LocalDateTime.now());
          Inquiry inquiry = mapper.map(inquiryServiceModel,Inquiry.class);
          inquiryRepository.save(inquiry);
    }

    @Override
    public List<InquiryViewModel> loadViewModels(Long id) {
        List<Inquiry>inquiries = inquiryRepository.findAllByOfferIdOrderByCreatedOnDesc(id);
        return inquiries.stream().map(i-> {
            InquiryViewModel viewModel = mapper.map(i, InquiryViewModel.class);
            viewModel.setId(i.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            viewModel.setCreatedOn(i.getCreatedOn().format(formatter));
            viewModel.setContactHour(i.getContactHour().name());
            return viewModel;})
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByOfferId(Long id) {
        List<Inquiry>inquiries = inquiryRepository.findAllByOfferIdOrderByCreatedOnDesc(id);
        for (Inquiry inquiry : inquiries) {
            inquiryRepository.deleteById(inquiry.getId());
        }
    }
}
