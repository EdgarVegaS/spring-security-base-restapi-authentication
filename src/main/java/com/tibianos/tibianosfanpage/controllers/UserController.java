package com.tibianos.tibianosfanpage.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tibianos.tibianosfanpage.models.request.UserDetailRequestModel;
import com.tibianos.tibianosfanpage.models.responses.PostRest;
import com.tibianos.tibianosfanpage.models.responses.UserRest;
import com.tibianos.tibianosfanpage.services.UserServiceInterface;
import com.tibianos.tibianosfanpage.shared.dto.PostDto;
import com.tibianos.tibianosfanpage.shared.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public UserRest getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        UserDto userDto = userService.getUser(email);
        UserRest userRest = new ModelMapper().map(userDto, UserRest.class);
        return userRest;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) {

        UserRest userToReturn = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, userToReturn);

        return userToReturn;
    }

    @GetMapping(path = "/posts")
    public List<PostRest> getPost() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        List<PostDto> listPost = userService.getUserPosts(email);

        List<PostRest> listToReturn = new ArrayList<>(); 

        for (PostDto postDto : listPost) {
            PostRest postRest = mapper.map(postDto, PostRest.class );
            if (postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0) {
                postRest.setExpired(true);
            }
            listToReturn.add(postRest);
        }

        return listToReturn;
    }
    
}
