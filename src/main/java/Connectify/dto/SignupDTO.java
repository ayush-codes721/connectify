package Connectify.dto;

import Connectify.models.enums.Role;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignupDTO {


    private String name;
    private String email;
    private String password;
    private Role role;
}
