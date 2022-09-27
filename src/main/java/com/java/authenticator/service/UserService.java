package com.java.authenticator.service;

import com.java.authenticator.user.User;
import com.java.authenticator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOD_FOUND = "usuário com email %s não encontrado";
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(
                String.format(USER_NOD_FOUND, email)));
    }

    public String signUpUser(User user){
        boolean userExists = repository
                .findByEmail(user.getEmail())
                .isPresent();

        if(userExists){
            throw new IllegalStateException("Email já usado");
        }
       String encodePassWord =  bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassWord);
        repository.save(user);

        // TODO: Enviar a confirmação do token

        return "it works";
    }
}
