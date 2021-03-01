package com.tibianos.tibianosfanpage.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tibianos.tibianosfanpage.models.request.PostCreateRequestModel;
import com.tibianos.tibianosfanpage.models.responses.OperationStatusModel;
import com.tibianos.tibianosfanpage.models.responses.PostRest;
import com.tibianos.tibianosfanpage.services.PostServiceInteface;
import com.tibianos.tibianosfanpage.services.UserServiceInterface;
import com.tibianos.tibianosfanpage.shared.dto.PostCreationDto;
import com.tibianos.tibianosfanpage.shared.dto.PostDto;
import com.tibianos.tibianosfanpage.shared.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostServiceInteface postService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping
    public PostRest createPost(@RequestBody PostCreateRequestModel postRequestModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        PostCreationDto creationDto = mapper.map(postRequestModel, PostCreationDto.class);

        creationDto.setUserEmail(email);

        logger.info("Creando Post user: {}",email);

        PostDto postToReturn = postService.createPost(creationDto);

        PostRest postRest = mapper.map(postToReturn, PostRest.class);

        if (postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0) {
            postRest.setExpired(true);
        }

        logger.info("Post Creado: {}",postRest.toString());

        return postRest;
    }

    @GetMapping(path = "/last")
    public List<PostRest> getLastPost() {

        List<PostDto> listPost = postService.getLastPost();

        List<PostRest> postRests = new ArrayList<>();

        for (PostDto postDto : listPost) {
            PostRest postRest = mapper.map(postDto, PostRest.class);

            postRests.add(postRest);
        }

        return postRests;
    }

    @GetMapping(path = "/{id}")
    public PostRest getPost(@PathVariable String id) {

        PostDto post = postService.getPost(id);

        PostRest postRest = mapper.map(post, PostRest.class);

        if (postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0) {
            postRest.setExpired(true);
        }

        if (postRest.getExposure().getId() == 1 || postRest.isExpired()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDto user = userService.getUser(authentication.getPrincipal().toString());

            if (user.getId() != post.getUser().getId()) {

                throw new RuntimeException("No tienes permisos para realizar esta operacion");
            }
        }
        return postRest;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deletePost(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setName("DELETE");

        postService.deletePost(id, user.getId());
        operationStatusModel.setResult("Success");

        return operationStatusModel;
    }

    @PutMapping(path = "/{id}")
    public PostRest updatePost(@RequestBody PostCreateRequestModel body, @PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        PostCreationDto postUpdateDto = mapper.map(body, PostCreationDto.class);

        PostDto postDto = postService.updatePostDto(id, user.getId(), postUpdateDto);

        PostRest postRest = mapper.map(postDto, PostRest.class);

        return postRest;
    }
}