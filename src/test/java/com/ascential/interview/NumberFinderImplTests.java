package com.ascential.interview;

import com.ascential.interview.core.CustomNumberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class NumberFinderImplTests {

    @Autowired
    private NumberFinderImpl numberFinder;

    @Test
    public void testReadFromFile_shouldReturnNineResults() {
        List<CustomNumberEntity> customNumberEntities = numberFinder.readFromFile("data/sample.json");
        assertTrue(customNumberEntities.size() == 9, "The returned entities should have 9 elements");
    }

    @Test
    public void testContains_shouldReturnTrue() {
        int valueToFind = 100;
        String filePath = "data/sample.json";

        List<CustomNumberEntity> toTest = numberFinder.readFromFile(filePath);
        assertTrue(numberFinder.contains(valueToFind, toTest) == true);
    }

    @Test
    public void testContains_shouldReturnFalse() {
        int valueToFind = 10;
        String filePath = "data/sample.json";

        List<CustomNumberEntity> toTest = numberFinder.readFromFile(filePath);
        assertTrue(numberFinder.contains(valueToFind, toTest) == false);
    }

    @Test
    public void testContainsByBinarySearch_shouldReturnTrue() {
        int valueToFind = 100;
        String filePath = "data/sample.json";

        List<CustomNumberEntity> toTest = numberFinder.getSortedNumericList(numberFinder.readFromFile(filePath));
        assertTrue(numberFinder.containsByBinarySearch(valueToFind, toTest) == true);
    }

    @Test
    public void testContainsByBinarySearch_shouldReturnFalse() {
        int valueToFind = 10;
        String filePath = "data/sample.json";

        List<CustomNumberEntity> toTest = numberFinder.getSortedNumericList(numberFinder.readFromFile(filePath));
        assertTrue(numberFinder.containsByBinarySearch(valueToFind, toTest) == false);
    }


}
