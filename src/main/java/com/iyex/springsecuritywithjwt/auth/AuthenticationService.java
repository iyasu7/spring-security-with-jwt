package com.iyex.springsecuritywithjwt.auth;

import com.iyex.springsecuritywithjwt.config.JwtService;
import com.iyex.springsecuritywithjwt.entity.Role;
import com.iyex.springsecuritywithjwt.entity.User;
import com.iyex.springsecuritywithjwt.repo.UserRepository;
import com.iyex.springsecuritywithjwt.token.Token;
import com.iyex.springsecuritywithjwt.token.TokenRepo;
import com.iyex.springsecuritywithjwt.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        var token = Token.builder()
            .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
            .build();
        tokenRepo.save(token);
    }
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty())
            return;
        validUserTokens.forEach((t->{
                t.setExpired(true);
                t.setRevoked(true);
        }));
        tokenRepo.saveAll(validUserTokens);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);

        saveUserToken(user,jwtToken);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
