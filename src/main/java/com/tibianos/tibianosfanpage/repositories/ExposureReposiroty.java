package com.tibianos.tibianosfanpage.repositories;

import com.tibianos.tibianosfanpage.entities.ExposureEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExposureReposiroty extends CrudRepository<ExposureEntity, Long> {
    
    ExposureEntity findById(long id);

}
