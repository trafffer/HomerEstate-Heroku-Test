package bg.softuni.homerestate.models.validators;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,Object> {
    private String first;
    private String second;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
      this.first=constraintAnnotation.first();
      this.second=constraintAnnotation.second();
      this.message=constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Object firstFieldValue = beanWrapper.getPropertyValue(first);
        Object secondFieldValue = beanWrapper.getPropertyValue(second);
        boolean valid;

        if (firstFieldValue == null){
            valid=secondFieldValue == null;
        }else{
            valid=firstFieldValue.equals(secondFieldValue);
        }

        if (!valid){
            context
                    .buildConstraintViolationWithTemplate(message).addPropertyNode(first).addConstraintViolation()
                    .buildConstraintViolationWithTemplate(message).addPropertyNode(second).addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
