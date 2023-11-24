package com.adobe.orderapp.api;

import com.adobe.orderapp.dto.Post;
import com.adobe.orderapp.dto.User;
import com.adobe.orderapp.client.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    record PostDTO(String title, String user) {}
    @GetMapping()
    public List<PostDTO> getPosts() {
        CompletableFuture<List<Post>> posts = postService.getPosts(); // non-blocking
        CompletableFuture<List<User>> users = postService.getUsers(); //non-blocking

       List<Post> postList = posts.join(); // blocked
       List<User> userList = users.join(); // blocked

       return postList.stream().map(post -> {
           String username = userList.stream()
                   .filter(user -> user.id() == post.userId()).findFirst().get().name();
           return new PostDTO(post.title(), username);
       }).collect(Collectors.toList());
    }
}
