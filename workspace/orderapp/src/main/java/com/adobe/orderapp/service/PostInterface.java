package com.adobe.orderapp.service;

import com.adobe.orderapp.dto.Post;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/posts", accept = "application/json", contentType = "application/json")
public interface PostInterface {
    @GetExchange()
    List<Post> getPosts();

    @GetExchange("/{id}")
    Post getPost(@PathVariable("id") long id);

    @PostExchange()
    Post addPost(@RequestBody Post post);
}
