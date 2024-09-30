package com.tech.razorpay.repo;

import com.tech.razorpay.entity.StudentOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentOrderRepo extends JpaRepository<StudentOrders, Long> {

    public StudentOrders findByRazorpayOrderId(String orderId);
}
