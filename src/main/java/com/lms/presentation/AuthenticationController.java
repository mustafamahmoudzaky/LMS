package com.lms.presentation;

import com.lms.constants.constants;
import com.lms.persistence.LoginResponse;
import com.lms.persistence.LoginUserDto;
import com.lms.persistence.RegisterUserDto;
import com.lms.persistence.User;
import com.lms.service.AuthenticationService;
import com.lms.service.JwtService;
import com.lms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
//        User registeredUser = authenticationService.signup(registerUserDto);
//
//        return ResponseEntity.ok(registeredUser);
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        if (!constants.ROLE_ADMIN.equals(registerUserDto.getRole())) {
            return ResponseEntity.status(403).body(constants.ERROR_UNAUTHORIZED);
        }
        if(userService.findById(registerUserDto.getId()) != null) {
            System.out.println("Duplicated Id");
            return ResponseEntity.status(409).body("Id already in use.");
        }

        if(userService.findByEmail(registerUserDto.getEmail()).isPresent()) {
            System.out.println("Duplicated Email");
            return ResponseEntity.status(409).body("Email already in use.");
        }

        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
