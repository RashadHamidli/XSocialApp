package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.PostRequest;
import org.social.dto.response.PostResponse;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repositories.PostRepository;
import org.social.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.StringTemplate.STR;


@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public List<PostResponse> getAllPost() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        return posts.stream().map(PostResponse::convertPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getAllPostByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        List<Post> posts = postRepository.findPostByUser(user);
        return posts.stream().map(PostResponse::convertPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse createPostByUser(String username, PostRequest postRequest) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        Post post = PostRequest.convertePostRequestToPost(postRequest);
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return PostResponse.convertPostToPostResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId} not found"));
        Optional.ofNullable(postRequest.postText()).ifPresent(foundPost::setPostText);
        Post savedPost = postRepository.save(foundPost);
        return PostResponse.convertPostToPostResponse(savedPost);
    }

    public Boolean deletePostByPostId(Long postId) {
        try {
            Post foundPost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId} not found"));
            postRepository.delete(foundPost);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
