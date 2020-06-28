package com.example.NumberManagement.controller;

import com.example.NumberManagement.Model.NumberModel;
import com.example.NumberManagement.exception.BadRequestException;
import com.example.NumberManagement.service.NumberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/numbers")
public class NumbersResource {

    private static final Logger LOGGER = LogManager.getLogger(NumbersResource.class);

    @Autowired
    private NumberService numberService;

    @RequestMapping(value = "insert/{number}")
    @ApiOperation(value = "Insert new number.", httpMethod = "POST")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NumberModel> insertNumber(@PathVariable("number") String number) {
        LOGGER.info("number {} is being added to db", number);
        NumberModel numberModel = numberService.insertNumber(number);
        if (numberModel == null) {
            throw new BadRequestException("number " + number + " is already exists in db");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(numberModel);
    }

    @RequestMapping(value = "")
    @ApiOperation(value = "Get all numbers.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<NumberModel>> getAllNumbers(@RequestParam("name") String order) {
        LOGGER.info("All numbers are being got from DB.");
        List<NumberModel> numberModels = numberService.getAllNumbers(order);
        return ResponseEntity.ok().body(numberModels);
    }

    @RequestMapping(value = "get/{number}")
    @ApiOperation(value = "Get number.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NumberModel> getNumber(@PathVariable("number") String number) {
        LOGGER.info("number id {} is being got from DB", number);
        NumberModel retrievedNumber = numberService.getNumber(number);
        return ResponseEntity.ok().body(retrievedNumber);
    }

    @RequestMapping(value = "/getMaxNumber")
    @ApiOperation(value = "Get max number.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NumberModel> getMaxNumber() {
        LOGGER.info("Getting Max Num");
        NumberModel maxNumber = numberService.getMaxNumber();
        return ResponseEntity.ok().body(maxNumber);
    }

    @RequestMapping(value = "getMinNumber")
    @ApiOperation(value = "Get min number.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NumberModel> getMinNumber() {
        LOGGER.info("Getting Min Num");
        NumberModel minNumber = numberService.getMinNumber();
        return ResponseEntity.ok().body(minNumber);
    }

    @RequestMapping(value = "delete/{number}")
    @ApiOperation(value = "Get min number.", httpMethod = "DELETE")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteNumber(@PathVariable("number") String number) {
        LOGGER.info("number id {} is being deleted from db", number);
        numberService.deleteNumber(number);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}