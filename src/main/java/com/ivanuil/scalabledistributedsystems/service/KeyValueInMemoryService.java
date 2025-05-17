package com.ivanuil.scalabledistributedsystems.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(
        value = "key-value.mode",
        havingValue = "in-memory"
)
public class KeyValueInMemoryService implements KeyValueService {

    private final Map<String, String> map = new HashMap<>();

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        var res = map.get(key);
        if (res == null)
            throw new IllegalStateException("No value found for key: " + key);
        return res;
    }

}
