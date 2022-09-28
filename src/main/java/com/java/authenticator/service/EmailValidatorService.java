package com.java.authenticator.service;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;
@Service
public class EmailValidatorService implements Predicate<String> {
    @Override
    public boolean test(String s) {
//         TODO: Regex para validação do email
        return true;
    }
}
