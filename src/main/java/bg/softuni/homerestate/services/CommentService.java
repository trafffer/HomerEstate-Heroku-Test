package bg.softuni.homerestate.services;

import bg.softuni.homerestate.models.entities.OfferComment;
import bg.softuni.homerestate.models.service.CommentServiceModel;
import bg.softuni.homerestate.models.view.CommentViewModel;

import java.util.List;

public interface CommentService {
    List<OfferComment>listAllComments();
    void saveComment(CommentServiceModel comment,Long id);
    List<CommentViewModel> findAllByOfferId(Long id);
    void deleteById(Long id);
}
