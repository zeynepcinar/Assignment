package com.example.NumberManagement.service;

import com.example.NumberManagement.exception.ResourceAlreadyExistsException;
import com.example.NumberManagement.exception.ResourceNotFoundException;
import com.example.NumberManagement.model.NumberModel;
import com.example.NumberManagement.repository.NumberRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.example.NumberManagement.TestUtil.createNumberEntries;
import static com.example.NumberManagement.TestUtil.createNumberEntry;
import static com.example.NumberManagement.TestUtil.createRandomNumber;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class NumberServiceTest {

    @Mock
    private NumberRepository numberRepositoryMock;

    private NumberService underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new NumberService(numberRepositoryMock);
    }

    @AfterMethod
    public void clean() {
        numberRepositoryMock.deleteAll();
    }

    @Test
    public void shouldInsertNumberSuccessfully() {
        // GIVEN
        int number = createRandomNumber();
        NumberModel numberToBeInserted = createNumberEntry(number);
        when(numberRepositoryMock.findByNumber(number)).thenReturn(null);
        when(numberRepositoryMock.insert(any(NumberModel.class))).thenReturn(numberToBeInserted);

        // WHEN
        NumberModel response = underTest.insertNumber(number);

        // THEN
        verify(numberRepositoryMock, times(1)).insert(any(NumberModel.class));
        verify(numberRepositoryMock, times(1)).findByNumber(number);
        assertNotNull(response);
        assertEquals(response.getNumber(), numberToBeInserted.getNumber());
        assertEquals(response.getDate(), numberToBeInserted.getDate());
    }

    @Test(expectedExceptions = ResourceAlreadyExistsException.class)
    public void shouldThrowBadRequestExceptionWhenInsertIfNumberIsAlreadyExists() {
        // GIVEN
        int number = createRandomNumber();
        NumberModel numberToBeInserted = createNumberEntry(number);
        when(numberRepositoryMock.findByNumber(number)).thenReturn(numberToBeInserted);
        when(numberRepositoryMock.insert(any(NumberModel.class))).thenReturn(numberToBeInserted);
        numberRepositoryMock.insert(numberToBeInserted);

        // WHEN
        NumberModel response = underTest.insertNumber(number);

        // THEN
        verify(numberRepositoryMock, times(1)).insert(any(NumberModel.class));
        verify(numberRepositoryMock, times(2)).findByNumber(number);
        assertNull(response);
    }

    @Test
    public void shouldGetAllNumbersSuccessfully() {
        // GIVEN
        List<NumberModel> numberModelList = createNumberEntries();
        when(numberRepositoryMock.findAllByOrderByNumberAsc()).thenReturn(numberModelList);

        // WHEN
        List<NumberModel> response = underTest.getAllNumbers(null);

        // THEN
        verify(numberRepositoryMock, times(1)).findAllByOrderByNumberAsc();
        assertNotNull(response);
        assertEquals(response.get(0).getNumber(), numberModelList.get(0).getNumber());
        assertEquals(response.get(0).getDate(), numberModelList.get(0).getDate());
    }

    @Test
    public void shouldGetAllNumbersSuccessfullyWithOrderParam() {
        // GIVEN
        List<NumberModel> numberModelList = createNumberEntries();
        when(numberRepositoryMock.findAllByOrderByNumberDesc()).thenReturn(numberModelList);

        // WHEN
        List<NumberModel> response = underTest.getAllNumbers("DESC");

        // THEN
        verify(numberRepositoryMock, times(1)).findAllByOrderByNumberDesc();
        assertNotNull(response);
        assertEquals(response.get(0).getNumber(), numberModelList.get(0).getNumber());
        assertEquals(response.get(0).getDate(), numberModelList.get(0).getDate());
    }

    @Test
    public void shouldGetNumberSuccessfully() throws Exception {
        // GIVEN
        int number = createRandomNumber();
        NumberModel existingNumber = createNumberEntry(number);
        when(numberRepositoryMock.findByNumber(number)).thenReturn(existingNumber);
        numberRepositoryMock.insert(existingNumber);

        // WHEN
        NumberModel response = underTest.getNumber(number);

        // THEN
        verify(numberRepositoryMock, times(1)).insert(any(NumberModel.class));
        verify(numberRepositoryMock, times(1)).findByNumber(number);
        assertNotNull(response);
        assertEquals(response.getNumber(), existingNumber.getNumber());
        assertEquals(response.getDate(), existingNumber.getDate());
    }

    @Test
    public void shouldGetMaxNumberSuccessfully() throws Exception {
        // GIVEN
        NumberModel minNumber = createNumberEntry(1);
        NumberModel maxNumber = createNumberEntry(2);
        when(numberRepositoryMock.findTopByOrderByNumberDesc()).thenReturn(maxNumber);
        numberRepositoryMock.insert(minNumber);
        numberRepositoryMock.insert(maxNumber);

        // WHEN
        NumberModel response = underTest.getMaxNumber();

        // THEN
        verify(numberRepositoryMock, times(1)).findTopByOrderByNumberDesc();
        assertNotNull(response);
        assertEquals(response.getNumber(), maxNumber.getNumber());
        assertEquals(response.getDate(), maxNumber.getDate());
    }

    @Test
    public void shouldGetMinNumberSuccessfully() {
        // GIVEN
        NumberModel minNumber = createNumberEntry(1);
        NumberModel maxNumber = createNumberEntry(2);
        when(numberRepositoryMock.findTopByOrderByNumberAsc()).thenReturn(minNumber);
        numberRepositoryMock.insert(minNumber);
        numberRepositoryMock.insert(maxNumber);

        // WHEN
        NumberModel response = underTest.getMinNumber();

        // THEN
        verify(numberRepositoryMock, times(1)).findTopByOrderByNumberAsc();
        assertNotNull(response);
        assertEquals(response.getNumber(), minNumber.getNumber());
        assertEquals(response.getDate(), minNumber.getDate());
    }

    @Test
    public void shouldDeleteNumberSuccessfully() {
        // GIVEN
        int number = createRandomNumber();
        NumberModel existingNumber = createNumberEntry(number);
        doNothing().when(numberRepositoryMock).delete(existingNumber);
        when(numberRepositoryMock.findByNumber(number)).thenReturn(existingNumber);
        numberRepositoryMock.insert(existingNumber);

        // WHEN
        underTest.deleteNumber(number);

        // THEN
        verify(numberRepositoryMock, times(1)).findByNumber(number);
        verify(numberRepositoryMock, times(1)).delete(existingNumber);
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void shouldThrowResourceNotfoundExceptionWhenNumberDoesNotExist() {
        // GIVEN
        int number = createRandomNumber();
        NumberModel existingNumber = createNumberEntry(number);
        numberRepositoryMock.insert(existingNumber);

        // WHEN
        underTest.deleteNumber(number);

        // THEN
        verify(numberRepositoryMock, times(0)).delete(existingNumber);
        verify(numberRepositoryMock, times(1)).findByNumber(number);
    }
}
