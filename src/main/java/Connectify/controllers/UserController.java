package Connectify.controllers;

import Connectify.Servcies.User.UserService;
import Connectify.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));

    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody  Map<String, Object> updates) {

        return ResponseEntity.ok(userService.updateUser(updates));
    }


}
