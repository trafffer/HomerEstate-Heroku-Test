package bg.softuni.homerestate.repositories;

import bg.softuni.homerestate.models.entities.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry>findAllByOfferIdOrderByCreatedOnDesc(Long id);
}
