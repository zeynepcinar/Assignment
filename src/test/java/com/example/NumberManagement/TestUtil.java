package com.example.NumberManagement;

import com.example.NumberManagement.Model.NumberModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestUtil {

    public static NumberModel createNumberEntry(String number) {
        return NumberModel.builder()
                .number(number)
                .date(new Date().toString())
                .build();
    }

    public static List<NumberModel> createNumberEntries() {
        List<NumberModel> numberModelList = new ArrayList<>();
        NumberModel minNumberEntry = createNumberEntry("10");
        NumberModel maxNumberEntry = createNumberEntry("20");
        numberModelList.add(minNumberEntry);
        numberModelList.add(maxNumberEntry);
        return numberModelList;
    }

    public static String createRandomNumber() {
        return UUID.randomUUID().toString();
    }

}

