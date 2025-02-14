package com.toyhe.app.Customer.Dtos.Response;

import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Tickets.Dtos.ReservationResponse;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerResponse(
        long customerID,
        long userAccountID,
        String customerName,
        String customerEmail,
        String customerAddress,
        String phoneNumber,
        long tripsNumber,
        List<ReservationResponse> tickets
) {
    public static CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerID(),
                customer.getUserAccountID(),
                customer.getCustomerName(),
                customer.getCustomerEmail(),
                customer.getCustomerAddress(),
                customer.getPhoneNumber(),
                customer.getTripsNumber(),
                customer.getTickets() != null ? customer.getTickets().stream()
                        .map(ReservationResponse::toDto)
                        .collect(Collectors.toList()) : List.of()
        );
    }
}
