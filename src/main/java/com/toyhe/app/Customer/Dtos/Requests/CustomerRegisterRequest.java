package com.toyhe.app.Customer.Dtos.Requests;

public record CustomerRegisterRequest(
        String customerName,
        String customerEmail,
        String customerAddress,
        String phoneNumber
) {}

