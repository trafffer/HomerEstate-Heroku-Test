package bg.softuni.homerestate.models.service;

import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.enums.ContactHours;

import java.time.LocalDateTime;

public class InquiryServiceModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private ContactHours contactHour;
    private String description;
    private LocalDateTime createdOn;
    private Offer offer;

    public InquiryServiceModel() {
    }


    public String getFirstName() {
        return firstName;
    }

    public InquiryServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public InquiryServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InquiryServiceModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public InquiryServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactHours getContactHour() {
        return contactHour;
    }

    public InquiryServiceModel setContactHour(ContactHours contactHour) {
        this.contactHour = contactHour;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InquiryServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public InquiryServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public InquiryServiceModel setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
