package Connectify.dto;


import Connectify.models.Post;
import Connectify.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class LikeDTO {
    private Long id;

    private UserDTO user;
    private PostDTO post;
}
