package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.OfferComment;
import bg.softuni.homerestate.models.service.CommentServiceModel;
import bg.softuni.homerestate.models.view.CommentViewModel;
import bg.softuni.homerestate.repositories.CommentRepository;
import bg.softuni.homerestate.services.CommentService;
import bg.softuni.homerestate.services.InquiryService;
import bg.softuni.homerestate.services.OfferService;
import bg.softuni.homerestate.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;
    private final OfferService offerService;
    private final UserService userService;
    private final InquiryService inquiryService;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper mapper, OfferService offerService, UserService userService, InquiryService inquiryService) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.offerService = offerService;
        this.userService = userService;
        this.inquiryService = inquiryService;
    }

    @Override
    public List<OfferComment> listAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void saveComment(CommentServiceModel comment,Long id) {
        comment.setCreatedOn(LocalDateTime.now());
        comment.setOffer(offerService.getOfferEntity(id));
        comment.setAuthor(userService.getUser());
        OfferComment newComment = mapper.map(comment,OfferComment.class);
        commentRepository.save(newComment);
    }

    @Override
    public List<CommentViewModel> findAllByOfferId(Long id) {
        List<OfferComment>comments = commentRepository.findAllByOfferId(id);
        return comments
                .stream()
                .map(c -> {
                   CommentViewModel comment =  mapper.map(c, CommentViewModel.class);
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                   comment.setCreatedOn(c.getCreatedOn().format(formatter));
                   return comment;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteByOfferId(id);
        inquiryService.deleteByOfferId(id);
        offerService.deleteOffer(id);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void deleteOldComments(){
        List<OfferComment>oldComments=
                commentRepository.findAllByCreatedOnBefore(LocalDateTime.now().minusDays(7));
        if (oldComments.isEmpty()){
            return;
        }
        for (OfferComment comment : oldComments) {
            commentRepository.deleteById(comment.getId());
        }
    }
}
