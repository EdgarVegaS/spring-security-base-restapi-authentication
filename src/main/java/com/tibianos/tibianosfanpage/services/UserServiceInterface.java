package com.tibianos.tibianosfanpage.services;

import java.util.List;

import com.tibianos.tibianosfanpage.shared.dto.PostDto;
import com.tibianos.tibianosfanpage.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {

    public UserDto createUser(UserDto user);
    public UserDto getUser(String email);
    public List<PostDto> getUserPosts(String email);
}
