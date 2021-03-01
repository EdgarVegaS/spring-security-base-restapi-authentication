package com.tibianos.tibianosfanpage;

import com.tibianos.tibianosfanpage.models.responses.UserRest;
import com.tibianos.tibianosfanpage.security.AppProperties;
import com.tibianos.tibianosfanpage.shared.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class TibianosfanpageApplication {

	public static void main(String[] args) {
		SpringApplication.run(TibianosfanpageApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder (){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringAppContext appContext(){
		return new SpringAppContext();
	}

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties(){
		return new AppProperties();
	}

	@Bean(name = "ModelMapper")
	public ModelMapper getModelMapper(){
		ModelMapper mapper = new ModelMapper();

		mapper.typeMap(UserDto.class, UserRest.class).addMappings( m -> m.skip(UserRest::setPost));

		return mapper;
	}

	@Bean(name = "restConnection")
	public RestTemplate restSapConnection(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder.build();
	}
}
