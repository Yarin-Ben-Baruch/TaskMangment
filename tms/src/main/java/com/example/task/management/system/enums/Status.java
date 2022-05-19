package com.example.task.management.system.enums;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    OPEN(0),
    IN_PROCESS(1),
    CLOSED(2);

    private int value;
    private static Map map = new HashMap<>();

    Status(int value) {
        this.value = value;
    }

    static {
        for (Status status : Status.values()) {
            map.put(status.value, status);
        }
    }

    public static Status valueOf(int status) {
        return (Status) map.get(status);
    }

    public int getValue() {
        return value;
    }
}
