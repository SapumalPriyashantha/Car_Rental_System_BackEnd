package lk.ijse.spring.repo;

import lk.ijse.spring.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepo extends JpaRepository<Payment, String> {
    @Query(value = "select pay_id from reservation_payment ORDER BY pay_id DESC LIMIT 1", nativeQuery = true)
    String getGenerateOrderId();
}