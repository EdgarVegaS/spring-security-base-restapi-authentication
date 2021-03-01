package com.tibianos.tibianosfanpage.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tibianos.tibianosfanpage.entities.ExposureEntity;
import com.tibianos.tibianosfanpage.entities.PostEntity;
import com.tibianos.tibianosfanpage.entities.UserEntity;
import com.tibianos.tibianosfanpage.repositories.ExposureReposiroty;
import com.tibianos.tibianosfanpage.repositories.PostRepository;
import com.tibianos.tibianosfanpage.repositories.UserRepository;
import com.tibianos.tibianosfanpage.shared.dto.PostCreationDto;
import com.tibianos.tibianosfanpage.shared.dto.PostDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements PostServiceInteface {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExposureReposiroty exposureRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public PostDto createPost(PostCreationDto post) {

        UserEntity userEntity = userRepository.findByEmail(post.getUserEmail());
        ExposureEntity exposureEntity = exposureRepository.findById(post.getExposureId());

        PostEntity postEntity = new PostEntity();
        postEntity.setUser(userEntity);
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setPostId(UUID.randomUUID().toString());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (post.getExpirationTime() * 60000)));

        PostEntity createdPOst = postRepository.save(postEntity);
        PostDto postToReturn = mapper.map(createdPOst, PostDto.class);
        return postToReturn;

    }

    @Override
    public List<PostDto> getLastPost() {

        long publicExposure = 2;
        List<PostEntity> postEntities = postRepository.getLastPublicPost(publicExposure,
                new Date(System.currentTimeMillis()));

        List<PostDto> dtos = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostDto sto = mapper.map(postEntity, PostDto.class);

            dtos.add(sto);
        }

        return dtos;
    }

    @Override
    public PostDto getPost(String postId) {
        
        PostEntity post = postRepository.getByPostId(postId);
        PostDto postToReturn = mapper.map(post, PostDto.class);
        return postToReturn;
    }

    @Override
    public void deletePost(String id, long userId) {
        
        PostEntity postEntity = postRepository.getByPostId(id);
        if (postEntity.getUser().getId() != userId) throw new RuntimeException("No puede realizar esta operacion");

        postRepository.delete(postEntity);

    }

    @Override
    public PostDto updatePostDto(String id, long userId, PostCreationDto creationDto) {
        PostEntity postEntity = postRepository.getByPostId(id);
        if (postEntity.getUser().getId() != userId) throw new RuntimeException("No puede realizar esta operacion");

        ExposureEntity exposureEntity = exposureRepository.findById(creationDto.getExposureId());
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(creationDto.getTitle());
        postEntity.setContent(creationDto.getContent());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (creationDto.getExpirationTime() * 60000)));

        PostEntity updatedPost = postRepository.save(postEntity);

        PostDto toReturn = mapper.map(updatedPost, PostDto.class);

        return toReturn;
    }

}
