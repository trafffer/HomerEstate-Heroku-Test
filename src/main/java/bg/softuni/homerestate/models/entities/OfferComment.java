package bg.softuni.homerestate.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class OfferComment extends BaseEntity{
    private UserEntity author;
    private LocalDateTime createdOn;
    private String textContent;
    private Offer offer;

    public OfferComment() {
    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public OfferComment setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    @Column(name = "created_on",nullable = false)
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public OfferComment setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @Column(name = "text",columnDefinition = "TEXT",nullable = false)
    public String getTextContent() {
        return textContent;
    }

    public OfferComment setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }


    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public OfferComment setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
