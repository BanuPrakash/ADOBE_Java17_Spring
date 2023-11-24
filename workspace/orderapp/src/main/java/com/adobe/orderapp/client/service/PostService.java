package com.adobe.orderapp.client.service;

import com.adobe.orderapp.dto.Post;
import com.adobe.orderapp.dto.User;
import com.adobe.orderapp.service.PostInterface;
import com.adobe.orderapp.service.UserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostInterface postInterface;
    private final UserInterface userInterface;
    @Async("asyncExecutor")
    public CompletableFuture<List<Post>> getPosts()  {
        System.out.println(Thread.currentThread());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return CompletableFuture.completedFuture(postInterface.getPosts());
    }
    @Async("asyncExecutor")
    public CompletableFuture<List<User>> getUsers() {
        System.out.println(Thread.currentThread());
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return CompletableFuture.completedFuture(userInterface.getUsers());
    }
}
