package bg.softuni.homerestate.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class CommentAddBindingModel {
    @NotBlank(message = "Comment cannot be empty")
    @Size(min = 5,max = 240,message = "Comment length must be between 5 and 240 characters")
    private String textContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future(message = "The date cannot be in the past")
    private LocalDateTime timeForVisit;

    public CommentAddBindingModel() {
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentAddBindingModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public LocalDateTime getTimeForVisit() {
        return timeForVisit;
    }

    public CommentAddBindingModel setTimeForVisit(LocalDateTime timeForVisit) {
        this.timeForVisit = timeForVisit;
        return this;
    }
}
