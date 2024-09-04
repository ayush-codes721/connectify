package Connectify.repo;

import Connectify.models.Post;
import Connectify.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);

}
