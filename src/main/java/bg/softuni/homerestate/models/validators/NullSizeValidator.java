package bg.softuni.homerestate.models.validators;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullSizeValidator implements ConstraintValidator<SizeWithNull,String> {
    private int min;
    private int max;
    private String message;

    @Override
    public void initialize(SizeWithNull constraintAnnotation) {
        this.min=constraintAnnotation.min();
        this.max= constraintAnnotation.max();
        this.message= constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean valid;
        if (value.length()==0){
            valid=true;
        }else{
            valid=(value.length()>=min&&value.length()<max);
        }
        if (!valid){
            context
                    .buildConstraintViolationWithTemplate(message).addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
