package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Component
public class ArrayListParamTSToStringMapper implements Mapper<ArrayList<String>, String> {
    @Override
    public String map(ArrayList<String> countVehicleType) {
        Integer value = 0;
        String responseValue = null;
        Map<String, Integer> countMap = new HashMap<>();
        for (String item : countVehicleType) {
            if (countMap.containsKey(item))
                countMap.put(item, countMap.get(item) + 1);
            else
                countMap.put(item, 1);
        }
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > value) {
                responseValue = entry.getKey();
                value = entry.getValue();
            }
        }
        return responseValue;
    }

    @Override
    public void mapFromInputInOutput(ArrayList<String> countVehicleType, String popularParamResponseBodies) {
    }
}
