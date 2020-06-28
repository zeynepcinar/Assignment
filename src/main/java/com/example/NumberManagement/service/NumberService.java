package com.example.NumberManagement.service;

import com.example.NumberManagement.model.NumberModel;
import com.example.NumberManagement.exception.BadRequestException;
import com.example.NumberManagement.exception.ResourceNotFoundException;
import com.example.NumberManagement.repository.NumberRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class NumberService {

    private static final Logger LOGGER = LogManager.getLogger(NumberService.class);
    private NumberRepository numberRepository;

    public NumberModel insertNumber(int number) {
        NumberModel num = numberRepository.findByNumber(number);
        if (num != null) {
            throw new BadRequestException("Number " + number + "is already inserted to DB.");
        }
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        NumberModel newNum = new NumberModel(number, formattedDate);

        NumberModel insertedNum = numberRepository.insert(newNum);
        LOGGER.info("Inserted number of {} in {} time successfully.", number, formattedDate);
        return insertedNum;
    }

    public List<NumberModel> getAllNumbers(String order) {
        LOGGER.debug("Service - Getting all numbers from DB.");
        if (order.equals("DESC")) {
            return numberRepository.findAllByOrderByNumberDesc();
        }
        return numberRepository.findAllByOrderByNumberAsc();
    }

    public NumberModel getNumber(int number) {
        LOGGER.debug("Service - Getting the numberModel by number: " + number);
        return numberRepository.findByNumber(number);
    }

    public NumberModel getMaxNumber() {
        LOGGER.debug("Getting the max number");
        return numberRepository.findTopByOrderByNumberDesc();
    }

    public NumberModel getMinNumber() {
        LOGGER.debug("Getting the min number");
        return numberRepository.findTopByOrderByNumberAsc();
    }

    public void deleteNumber(int number) {
        LOGGER.debug("Service - Deleting the numberModel by number: " + number);
        NumberModel numberModel = numberRepository.findByNumber(number);
        if (numberModel == null) {
            LOGGER.info("There is no number {} in db", number);
            throw new ResourceNotFoundException("Number " + number + "does not exist in db");
        }
        numberRepository.delete(numberModel);
    }
}