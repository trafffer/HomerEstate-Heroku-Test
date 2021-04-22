package bg.softuni.homerestate.web;



import bg.softuni.homerestate.models.view.InquiryViewModel;
import bg.softuni.homerestate.services.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/details")
public class OfferInquiriesController {
    private final InquiryService inquiryService;

    public OfferInquiriesController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }


    @GetMapping("/all/{id}")
    private ResponseEntity<List<InquiryViewModel>> findAll(@PathVariable("id")Long id){
        return ResponseEntity.ok()
                .body(inquiryService.loadViewModels(id));
    }
}
