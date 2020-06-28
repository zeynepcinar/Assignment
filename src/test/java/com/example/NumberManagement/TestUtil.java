package com.example.NumberManagement;

import com.example.NumberManagement.model.NumberModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestUtil {

    public static NumberModel createNumberEntry(int number) {
        return NumberModel.builder()
                .number(number)
                .date(new Date().toString())
                .build();
    }

    public static List<NumberModel> createNumberEntries() {
        List<NumberModel> numberModelList = new ArrayList<>();
        NumberModel minNumberEntry = createNumberEntry(1);
        NumberModel maxNumberEntry = createNumberEntry(2);
        numberModelList.add(minNumberEntry);
        numberModelList.add(maxNumberEntry);
        return numberModelList;
    }

    public static int createRandomNumber() {
        return (int) Math.random();
    }

}

