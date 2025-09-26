package io.cklau1001.circuitbreaker1.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Student(String id, String name, String grade) {

    public void sayHi() {
      log.info("sayHi: " + this);
    }
}
