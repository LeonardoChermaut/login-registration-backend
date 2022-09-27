package com.java.authenticator.registration.service;

import com.java.authenticator.user.User;
import com.java.authenticator.user.role.UserRole;
import com.java.authenticator.registration.resquest.RegistrationRequest;
import com.java.authenticator.registration.validator.EmailValidator;
import com.java.authenticator.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("O email não é valido");
        }
        return userService.signUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }
}
