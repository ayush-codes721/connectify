package Connectify.controllers;

import Connectify.Servcies.auth.AuthService;
import Connectify.dto.LoginDTO;
import Connectify.dto.SignupDTO;
import Connectify.dto.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse httpServletResponse) {

        String token = authService.login(loginDTO);
        httpServletResponse.addCookie(authService.createCookie(token));

        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    ResponseEntity<UserDTO> signup(@RequestBody SignupDTO signupDTO) {

        return ResponseEntity.ok(authService.signup(signupDTO));
    }

}
