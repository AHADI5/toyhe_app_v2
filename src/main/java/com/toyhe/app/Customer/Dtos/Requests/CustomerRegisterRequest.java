package com.toyhe.app.Customer.Dtos.Requests;

public record CustomerRegisterRequest(
        long userAccountID,
        String customerName,
        String customerEmail,
        String customerAddress,
        String phoneNumber
) {}

