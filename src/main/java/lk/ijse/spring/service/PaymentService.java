package lk.ijse.spring.service;

import lk.ijse.spring.dto.PaymentDTO;

public interface PaymentService {
    void PaymentForReservation(PaymentDTO dto);
    String generatePaymentId();
}