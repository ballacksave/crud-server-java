package com.ballacksave.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGen {
    public String generate() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString();
    }
}
