package Connectify.Servcies.User;

import Connectify.dto.UserDTO;
import Connectify.models.User;
import Connectify.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() -> new BadCredentialsException("user not found"));
    }

    public User findUserById(Long id) {


        return userRepo.findById(id).orElseThrow(() -> new BadCredentialsException("user not found/invalid id"));
    }


    @Transactional
    public UserDTO getUserById(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new BadCredentialsException("user not found invalid id"));

        return modelMapper.map(user, UserDTO.class);
    }


    public UserDTO getProfile() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(Map<String, Object> updates) {
        System.out.println(updates);
        User userL = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findById(userL.getId()).orElseThrow(() -> new BadCredentialsException("user not found"));

        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(User.class, field);
            fieldToBeUpdated.setAccessible(true);
//            System.out.println("Updating field: " + field + " with value: " + value);
            ReflectionUtils.setField(fieldToBeUpdated, user, value);
        });

        User updatedUser = userRepo.save(user);
        System.out.println(user == updatedUser);

        return modelMapper.map(user, UserDTO.class);


    }
}
