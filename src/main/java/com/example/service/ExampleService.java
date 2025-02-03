package com.example.service;

import com.example.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    public String getExampleData(String input) {
        if (input == null || input.isBlank()) {
            throw new CustomException("Input cannot be null or empty.");
        }
        return "Valid Input: " + input;
    }
}