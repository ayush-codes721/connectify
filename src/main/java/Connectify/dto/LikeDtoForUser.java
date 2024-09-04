package Connectify.dto;

import Connectify.models.Post;
import lombok.Data;

@Data
public class LikeDtoForUser {
    private Long id;
    private PostDTO post;

}
