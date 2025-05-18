package com.ivanuil.scalabledistributedsystems.service;

public interface KeyValueService {

    void put(String key, String value);

    String get(String key);

}
