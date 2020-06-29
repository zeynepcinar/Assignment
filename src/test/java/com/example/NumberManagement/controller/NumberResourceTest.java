package com.example.NumberManagement.controller;

import com.example.NumberManagement.exception.ResourceNotFoundException;
import com.example.NumberManagement.model.NumberModel;
import com.example.NumberManagement.service.NumberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.example.NumberManagement.TestUtil.createNumberEntries;
import static com.example.NumberManagement.TestUtil.createNumberEntry;
import static com.example.NumberManagement.TestUtil.createRandomNumber;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class NumberResourceTest {

    @Mock
    private NumberService numberServiceMock;

    @InjectMocks
    private NumbersResource underTest;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        underTest = new NumbersResource(numberServiceMock);
    }

    @Test
    public void shouldInsertNumber() throws Exception {
        // GIVEN
        int number = createRandomNumber();
        NumberModel numberToBeInserted = createNumberEntry(number);
        when(numberServiceMock.insertNumber(number)).thenReturn(numberToBeInserted);

        // WHEN
        final NumberModel responseNumber = underTest
                .insertNumber(number);

        // THEN
        assertNotNull(responseNumber);
        assertEquals(responseNumber.getNumber(), numberToBeInserted.getNumber());
        assertEquals(responseNumber.getDate(), numberToBeInserted.getDate());

        verify(numberServiceMock, times(1)).insertNumber(number);
    }

    @Test
    public void shouldGetAllNumbers() throws ResourceNotFoundException {
        // GIVEN
        List<NumberModel> numberModelList = createNumberEntries();
        when(numberServiceMock.getAllNumbers("ASC")).thenReturn(numberModelList);

        // WHEN
        List<NumberModel> responseNumberList = underTest.getAllNumbers("ASC");

        // THEN
        assertEquals(responseNumberList.get(0).getNumber(), numberModelList.get(0).getNumber());
        assertEquals(responseNumberList.get(0).getDate(), numberModelList.get(0).getDate());
        verify(numberServiceMock, times(1)).getAllNumbers("ASC");
    }

    @Test
    public void shouldGetNumber() throws ResourceNotFoundException {
        // GIVEN
        int number = createRandomNumber();
        NumberModel numberToBeGot = createNumberEntry(number);
        when(numberServiceMock.insertNumber(number)).thenReturn(numberToBeGot);
        when(numberServiceMock.getNumber(number)).thenReturn(numberToBeGot);

        // WHEN
        final NumberModel responseNumber = underTest.getNumber(number);

        // THEN
        assertNotNull(responseNumber);
        verify(numberServiceMock, times(1)).getNumber(number);
    }

    @Test
    public void shouldGetMaxNumber() throws ResourceNotFoundException {
        // GIVEN
        List<NumberModel> numberModelList = createNumberEntries();
        when(numberServiceMock.getMaxNumber()).thenReturn(numberModelList.get(1));

        // WHEN
        final NumberModel responseNumber = underTest.getMaxNumber();

        // THEN
        assertNotNull(responseNumber);
        verify(numberServiceMock, times(1)).getMaxNumber();
    }

    @Test
    public void shouldGetMinNumber() throws ResourceNotFoundException {
        // GIVEN
        List<NumberModel> numberModelList = createNumberEntries();
        when(numberServiceMock.getMinNumber()).thenReturn(numberModelList.get(0));

        // WHEN
        final NumberModel responseNumber = underTest.getMinNumber();

        // THEN
        assertNotNull(responseNumber);
        verify(numberServiceMock, times(1)).getMinNumber();
    }

    @Test
    public void shouldDeleteNumber() throws ResourceNotFoundException {
        // GIVEN
        int numberIdToDelete = createRandomNumber();
        doNothing().when(numberServiceMock).deleteNumber(numberIdToDelete);

        // WHEN
        underTest.deleteNumber(numberIdToDelete);

        // THEN
        verify(numberServiceMock, times(1)).deleteNumber(numberIdToDelete);
    }


}
