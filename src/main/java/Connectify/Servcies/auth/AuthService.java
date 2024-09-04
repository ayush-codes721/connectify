package Connectify.Servcies.auth;

import Connectify.Servcies.JWT.JwtService;
import Connectify.dto.LoginDTO;
import Connectify.dto.SignupDTO;
import Connectify.dto.UserDTO;
import Connectify.models.User;
import Connectify.repo.UserRepo;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public UserDTO signup(SignupDTO signupDTO) {

        User user = modelMapper.map(signupDTO, User.class);
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        User savedUser = userRepo.save(user);


        return modelMapper.map(savedUser, UserDTO.class);
    }


    public String login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        User user = (User) authentication.getPrincipal();

        String token = jwtService.createToken(user);

        return token;


    }

    public Cookie createCookie(String token){

        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);

        return  cookie;
    }
}
