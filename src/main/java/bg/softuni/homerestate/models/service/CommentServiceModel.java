package bg.softuni.homerestate.models.service;

import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.UserEntity;

import java.time.LocalDateTime;

public class CommentServiceModel {
    private UserEntity author;
    private LocalDateTime createdOn;
    private String textContent;
    private LocalDateTime timeForVisit;
    private Offer offer;

    public CommentServiceModel() {
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentServiceModel setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public CommentServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentServiceModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public LocalDateTime getTimeForVisit() {
        return timeForVisit;
    }

    public CommentServiceModel setTimeForVisit(LocalDateTime timeForVisit) {
        this.timeForVisit = timeForVisit;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public CommentServiceModel setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
