package com.java.authenticator.registration.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
//         TODO: Regex para validação do email

        return true;
    }
}
