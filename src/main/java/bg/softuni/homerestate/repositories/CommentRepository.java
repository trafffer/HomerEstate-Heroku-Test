package bg.softuni.homerestate.repositories;

import bg.softuni.homerestate.models.entities.OfferComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<OfferComment,Long> {
    List<OfferComment> findAllByOfferId(Long id);
    List<OfferComment> findAllByCreatedOnBefore(LocalDateTime now);
    void deleteByOfferId(Long id);
}
