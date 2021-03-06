package com.example.NumberManagement.repository;

import com.example.NumberManagement.model.NumberModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Number repository.
 */
@Repository
public interface NumberRepository extends MongoRepository<NumberModel, String> {

    NumberModel findByNumber(int number);

    NumberModel findTopByOrderByNumberAsc();

    NumberModel findTopByOrderByNumberDesc();

    List<NumberModel> findAllByOrderByNumberAsc();

    List<NumberModel> findAllByOrderByNumberDesc();

}

