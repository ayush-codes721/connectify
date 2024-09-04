package Connectify.Servcies;

import Connectify.Servcies.User.UserService;
import Connectify.dto.PostDTO;
import Connectify.dto.UserDTO;
import Connectify.models.Post;
import Connectify.models.User;
import Connectify.models.enums.Role;
import Connectify.repo.PostRepo;
import Connectify.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UserRepo userRepo;

    public List<PostDTO> getAllPost() {

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!loggedInUser.getRole().toString().equals(Role.ADMIN.toString())) {

            throw new RuntimeException("unauthorized access 🙂");

        }

        List<PostDTO> postDTOS = postRepo.findAll()
                .stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();

        return postDTOS;
    }

    @Transactional
    public  PostDTO createPost(PostDTO postDTO) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findUserById(loggedInUser.getId());


        Post post = modelMapper.map(postDTO, Post.class);

        post.setUser(user);
        Post savedPost = postRepo.save(post);

        user.getPosts().add(savedPost);
        userRepo.save(user);

        return modelMapper.map(savedPost, PostDTO.class);
    }

    public List<PostDTO> getAllPostOfLoggedInUser() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findUserById(loggedInUser.getId());

        List<Post> posts = postRepo.findByUser(user);

        List<PostDTO> postDTOS = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).toList();

        return postDTOS;


    }


}
