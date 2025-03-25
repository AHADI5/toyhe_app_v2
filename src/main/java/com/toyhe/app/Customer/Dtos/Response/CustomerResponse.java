package com.toyhe.app.Customer.Dtos.Response;

import com.toyhe.app.Auth.Dtos.Responses.AddressResponse;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Tickets.Dtos.OperatorResponse;
import com.toyhe.app.Tickets.Dtos.ReservationResponse;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerResponse(
        long customerID,
        String customerName,
        String customerEmail,
        AddressResponse address,
        String phoneNumber,
        long tripsNumber,
        List<ReservationResponse> tickets
) {
    public static CustomerResponse fromEntity(Customer customer , OperatorResponse operatorResponse) {

        return new CustomerResponse(
                customer.getCustomerID(),
                customer.getCustomerName(),
                customer.getCustomerEmail(),
                AddressResponse.fromEntity(customer.getAddress()),
                customer.getPhoneNumber(),
                customer.getTripsNumber(),
                customer.getTickets() != null ? customer.getTickets().stream()
                        .map(ticket -> ReservationResponse.toDto(ticket, operatorResponse))
                        .collect(Collectors.toList()) : List.of()
        );
    }
}
