package Connectify.dto;

import Connectify.models.enums.Role;
import lombok.Data;

@Data
public class UserDtoForPost {

    private Long id;

    private String name;

    private String email;
    private Role role;
}
