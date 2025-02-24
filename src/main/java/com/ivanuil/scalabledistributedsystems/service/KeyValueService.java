package com.ivanuil.scalabledistributedsystems.service;

import com.ivanuil.scalabledistributedsystems.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeyValueService {

    private final Map<String, String> map = new HashMap<>();

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        var res = map.get(key);
        if (res == null)
            throw new NotFoundException("No value found for key: " + key);
        return res;
    }

}
