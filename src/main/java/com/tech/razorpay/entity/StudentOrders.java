package com.tech.razorpay.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "student_orders", schema = "payment_schema")
public class StudentOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String name;
    private String phone;
    private String email;
    private String course;
    private Integer amount;
    private String orderStatus;
    private String razorpayOrderId;
}
