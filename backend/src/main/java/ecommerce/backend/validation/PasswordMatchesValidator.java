package ecommerce.backend.validation;


import ecommerce.backend.dto.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        SignupRequest user = (SignupRequest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
