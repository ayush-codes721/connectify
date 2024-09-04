package Connectify.dto;

import Connectify.models.Post;
import Connectify.models.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class UserDTO {
    private Long id;

    private String name;

    private String email;
    private String password;
    private Role role;
    List<PostDTO> posts;
    List<LikeDtoForUser> likes;
}
