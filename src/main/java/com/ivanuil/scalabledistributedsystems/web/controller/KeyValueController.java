package com.ivanuil.scalabledistributedsystems.web.controller;

import com.ivanuil.scalabledistributedsystems.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/storage")
public class KeyValueController {

    @Autowired
    public KeyValueController(KeyValueService keyValueService) {
        this.keyValueService = keyValueService;
    }

    private final KeyValueService keyValueService;

    @PutMapping("/put")
    public void put(@RequestParam String key, @RequestParam String value) {
        keyValueService.put(key, value);
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return keyValueService.get(key);
    }

}
