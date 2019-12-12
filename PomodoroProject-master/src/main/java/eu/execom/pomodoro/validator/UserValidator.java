package eu.execom.pomodoro.validator;

import eu.execom.pomodoro.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        User user = (User) object;

        if (user.getPassword().length() < 5) {
            errors.rejectValue("password", "Length", "Password must be at least 5 characters");
        }

    }
}
