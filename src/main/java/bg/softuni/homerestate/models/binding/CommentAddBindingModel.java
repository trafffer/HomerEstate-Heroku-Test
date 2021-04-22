package bg.softuni.homerestate.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class CommentAddBindingModel {
    @NotBlank(message = "Comment cannot be empty")
    @Size(min = 5,max = 240,message = "Comment length must be between 5 and 240 characters")
    private String textContent;

    public CommentAddBindingModel() {
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentAddBindingModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

}
