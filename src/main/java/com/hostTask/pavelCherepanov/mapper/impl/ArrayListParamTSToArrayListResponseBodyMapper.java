package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.model.response.GetTSPopularParamResponseBody;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class ArrayListParamTSToArrayListResponseBodyMapper implements Mapper<ArrayList<String>, ArrayList<GetTSPopularParamResponseBody>> {

    @Override
    public ArrayList<GetTSPopularParamResponseBody> map(ArrayList<String> countVehicleType) {
        ArrayList<GetTSPopularParamResponseBody> tsTypeResponseBody = new ArrayList<>();
        GetTSPopularParamResponseBody typeResponseBody = new GetTSPopularParamResponseBody();
        Map<String, Integer> countMap = new HashMap<>();

        for (String item : countVehicleType) {
            if (countMap.containsKey(item)) {
                countMap.put(item, countMap.get(item) + 1);
            }
            else
                countMap.put(item, 1);
        }
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            typeResponseBody.setName(entry.getKey());
            typeResponseBody.setCount(entry.getValue());
            tsTypeResponseBody.add(typeResponseBody);
            typeResponseBody = new GetTSPopularParamResponseBody();
        }
        return tsTypeResponseBody;
    }

    @Override
    public void mapFromInputInOutput(ArrayList<String> countVehicleType, ArrayList<GetTSPopularParamResponseBody> popularParamResponseBodies) {
    }
}
