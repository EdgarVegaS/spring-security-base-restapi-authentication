package com.tibianos.tibianosfanpage.services;

import java.util.List;

import com.tibianos.tibianosfanpage.shared.dto.PostCreationDto;
import com.tibianos.tibianosfanpage.shared.dto.PostDto;

public interface PostServiceInteface {

    public PostDto createPost(PostCreationDto creationDto);

    public List<PostDto> getLastPost();

    public PostDto getPost(String postId);

    public void deletePost(String id, long userId);

    public PostDto updatePostDto(String id, long userId, PostCreationDto creationDto);

}
