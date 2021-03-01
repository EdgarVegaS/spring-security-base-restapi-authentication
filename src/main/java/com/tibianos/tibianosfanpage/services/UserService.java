package com.tibianos.tibianosfanpage.services;

import com.tibianos.tibianosfanpage.repositories.PostRepository;
import com.tibianos.tibianosfanpage.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tibianos.tibianosfanpage.entities.PostEntity;
import com.tibianos.tibianosfanpage.entities.UserEntity;
import com.tibianos.tibianosfanpage.shared.dto.PostDto;
import com.tibianos.tibianosfanpage.shared.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bcrypt;

    @Autowired
    PostRepository postRepo;
    
    @Autowired
    ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new RuntimeException("El Correo ya existe");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(bcrypt.encode(user.getPassword()));
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userToReturn);

        return userToReturn;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        UserDto userToReturn = new UserDto();

        BeanUtils.copyProperties(userEntity, userToReturn);

        return userToReturn;
    }

    @Override
    public List<PostDto> getUserPosts(String email) {

        UserEntity user = userRepository.findByEmail(email);
        
        List<PostEntity> listPosts = postRepo.getByUserIdOrderByCreatedAtDesc(user.getId());

        List<PostDto> ListPostDto = new ArrayList<>(); 

        for (PostEntity postEntity : listPosts) {

            PostDto postDto = mapper.map(postEntity, PostDto.class);

            ListPostDto.add(postDto);
        }
        
        return ListPostDto;
    }
    
}
