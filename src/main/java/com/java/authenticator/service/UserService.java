package com.java.authenticator.service;

import com.java.authenticator.token.Token;
import com.java.authenticator.user.AppUser;
import com.java.authenticator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private static final String USER_NOD_FOUND = "usuário com email %s não encontrado";
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(
                String.format(USER_NOD_FOUND, email)));
    }

    public int enableUser(String email) {
        return repository.enableUser(email);
    }

    public String signUpUser(AppUser user){
        boolean userExists = repository
                .findByEmail(user.getEmail())
                .isPresent();

        if(userExists){
            throw new IllegalStateException("Email já usado");
        }
       String encodePassWord =  bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassWord);
        repository.save(user);
        String token = UUID.randomUUID().toString();
        Token confirmationToken = new Token(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        tokenService.save(confirmationToken);
        // TODO: ENVIAR EMAIL
        return token;

    }
}
