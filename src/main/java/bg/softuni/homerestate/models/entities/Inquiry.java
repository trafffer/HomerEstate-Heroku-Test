package bg.softuni.homerestate.models.entities;

import bg.softuni.homerestate.models.entities.enums.ContactHours;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiries")
public class Inquiry extends BaseEntity{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private ContactHours contactHour;
    private String description;
    private LocalDateTime createdOn;
    private Offer offer;

    public Inquiry() {
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public Inquiry setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(name = "last_name",nullable = false)
    public String getLastName() {
        return lastName;
    }

    public Inquiry setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(name = "phone_number",nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Inquiry setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Column(name = "email",nullable = false)
    public String getEmail() {
        return email;
    }

    public Inquiry setEmail(String email) {
        this.email = email;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public ContactHours getContactHour() {
        return contactHour;
    }

    public Inquiry setContactHour(ContactHours contactHour) {
        this.contactHour = contactHour;
        return this;
    }

    @Column(name = "description",columnDefinition = "TEXT",nullable = false)
    public String getDescription() {
        return description;
    }

    public Inquiry setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(name = "created_on",nullable = false)
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Inquiry setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public Inquiry setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
