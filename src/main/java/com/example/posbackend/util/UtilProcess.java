package com.example.posbackend.util;

import java.util.UUID;

public class UtilProcess {
    public static String generateID(){
        return UUID.randomUUID().toString();
    }
}
