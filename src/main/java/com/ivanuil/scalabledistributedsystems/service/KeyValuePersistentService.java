package com.ivanuil.scalabledistributedsystems.service;

import com.ivanuil.scalabledistributedsystems.exception.NotFoundException;
import com.ivanuil.scalabledistributedsystems.repository.KeyValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        value = "key-value.mode",
        havingValue = "persistent"
)
public class KeyValuePersistentService implements KeyValueService {

    public KeyValuePersistentService(KeyValueRepository keyValueRepository) {
        this.keyValueRepository = keyValueRepository;
    }

    private final KeyValueRepository keyValueRepository;

    public void put(String key, String value) {
        keyValueRepository.put(key, value);
    }

    public String get(String key) {
        var res = keyValueRepository.get(key);
        if (res == null)
            throw new NotFoundException("No value found for key: " + key);
        return res;
    }

}
