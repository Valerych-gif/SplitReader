package ru.valerych.splitreader.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.valerych.splitreader.config.security.JWTTokenUtils;
import ru.valerych.splitreader.dto.jwt.JwtRequest;
import ru.valerych.splitreader.dto.jwt.JwtResponse;
import ru.valerych.splitreader.responses.ReqErrorResponse;
import ru.valerych.splitreader.services.UserServiceImpl;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;
    private final JWTTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authuser")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new ReqErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
