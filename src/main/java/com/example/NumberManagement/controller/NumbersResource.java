package com.example.NumberManagement.controller;

import com.example.NumberManagement.model.NumberModel;
import com.example.NumberManagement.service.NumberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping(value = "/{number}")
    @ApiOperation(value = "Insert new number.", httpMethod = "POST")
    @ResponseStatus(HttpStatus.OK)
    public NumberModel insertNumber(@PathVariable("number") int number) {
        LOGGER.info("number {} is being added to db", number);
        return numberService.insertNumber(number);
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Get all numbers.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public List<NumberModel> getAllNumbers(@RequestParam(required = false) String order) {
        LOGGER.info("All numbers are being got from db.");
        return numberService.getAllNumbers(order);
    }

    @GetMapping(value = "/{number}")
    @ApiOperation(value = "Get number.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public NumberModel getNumber(@PathVariable("number") int number) {
        LOGGER.info("number id {} is being got from db.", number);
        return numberService.getNumber(number);
    }

    @GetMapping(value = "/max")
    @ApiOperation(value = "Get max number.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public NumberModel getMaxNumber() {
        return numberService.getMaxNumber();
    }

    @GetMapping(value = "/min")
    @ApiOperation(value = "Get min number.", httpMethod = "GET")
    @ResponseStatus(HttpStatus.OK)
    public NumberModel getMinNumber() {
        LOGGER.info("Getting Min Num");
        return numberService.getMinNumber();
    }

    @DeleteMapping(value = "/{number}")
    @ApiOperation(value = "Delete number.", httpMethod = "DELETE")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNumber(@PathVariable("number") int number) {
        LOGGER.info("number id {} is being deleted from db", number);
        numberService.deleteNumber(number);
    }
}