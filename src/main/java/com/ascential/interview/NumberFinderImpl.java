package com.ascential.interview;

import com.ascential.interview.core.CustomNumberEntity;
import com.ascential.interview.core.FastestComparator;
import com.ascential.interview.core.NumberFinder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class NumberFinderImpl implements NumberFinder {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FastestComparator comparator;

    @Override
    public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
        if (list.isEmpty()) return false;

        return list.stream()
                .parallel()
                .filter(e -> NumberUtils.isCreatable(e.getNumber()))
                .filter(e -> comparator.compare(valueToFind, e) == 0)
                .findAny()
                .isPresent();
    }

    // filePath is the classpath for the json file, like data/sample.json
    @Override
    public List<CustomNumberEntity> readFromFile(String filePath) {
        List<CustomNumberEntity> result = new ArrayList<>();
        try {
            URL resource = getClass().getClassLoader().getResource(filePath);
            if (nonNull(resource)) {
                File file = new File(resource.toURI());
                result = objectMapper
                        .readValue(file, new TypeReference<List<CustomNumberEntity>>(){});
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    // sort by ascending order O(n log(n)) Quicksort
    public List<CustomNumberEntity> getSortedNumericList(List<CustomNumberEntity> list) {
        return list.stream().filter(e -> NumberUtils.isCreatable(e.getNumber()))
                .sorted((e1, e2) -> comparator.compare(NumberUtils.createInteger(e1.getNumber()), e2))
                .collect(Collectors.toList());
    }

    public boolean containsByBinarySearch(int valueToFind, List<CustomNumberEntity> filteredAndSorted) {
        if (filteredAndSorted.isEmpty()) return false;

        int L =0;
        int R = filteredAndSorted.size() - 1;

        while (L <= R) {
            int mid = L + (R - L) / 2;
            if (comparator.compare(valueToFind, filteredAndSorted.get(mid)) == 0) {
                return true;
            } else if (comparator.compare(valueToFind, filteredAndSorted.get(mid)) < 0) {
                // target < mid
                R = mid - 1;
            } else if (comparator.compare(valueToFind, filteredAndSorted.get(mid)) > 0){
                // target > mid
                L = mid + 1;
            }
        }

        return false;
    }

}
