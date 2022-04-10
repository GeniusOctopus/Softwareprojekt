package com.softwareproject.backend.model;

import java.util.HashMap;
import java.util.Map;

public class DurationStatisticData {

    private Map<Integer, Integer> durationData;

    public DurationStatisticData() {

        durationData = new HashMap<>();
        durationData.put(1, 0);
        durationData.put(2, 0);
        durationData.put(3, 0);
        durationData.put(4, 0);
        durationData.put(5, 0);
        durationData.put(6, 0);
        durationData.put(7, 0);
        durationData.put(8, 0);
        durationData.put(9, 0);
        durationData.put(10, 0);
    }

    public DurationStatisticData(Map<Integer, Integer> durationData) {
        this.durationData = durationData;
    }

    public Map<Integer, Integer> getDurationData() {
        return durationData;
    }

    public void setDurationData(Map<Integer, Integer> durationData) {
        this.durationData = durationData;
    }

    public void increaseDurationData(int key){

        durationData.put(key, durationData.get(key) +1);
    }
}
