package Connectify.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;

    private String title;
    private String content;
    private UserDtoForPost user;

}
