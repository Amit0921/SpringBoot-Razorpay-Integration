package com.tech.razorpay.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.tech.razorpay.entity.StudentOrders;
import com.tech.razorpay.repo.StudentOrderRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentOrderRepo studentOrderRepo;

    private RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    String razorpayKey;

    @Value("${razorpay.secret.key}")
    String razorpaySecret;

    public StudentOrders createOrder(StudentOrders studentOrders) throws RazorpayException {

        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", studentOrders.getAmount() * 100); // amount in paisa (a*100)
        orderReq.put("currency", "INR");
        orderReq.put("receipt", studentOrders.getEmail());

        this.razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
        //Create RazorPay Order
        Order razorpayOrder = razorpayClient.orders.create(orderReq);

        studentOrders.setRazorpayOrderId(razorpayOrder.get("id"));
        studentOrders.setOrderStatus(razorpayOrder.get("status"));

        studentOrderRepo.save(studentOrders);

        return studentOrders;
    }

    public StudentOrders updateOrder(Map<String, String> resPayLoad){
        String razorpayOrderId = resPayLoad.get("razorpay_order_id");
        if (razorpayOrderId == null || razorpayOrderId.isEmpty()) {
            throw new IllegalArgumentException("Invalid razorpay_order_id");
        }
        StudentOrders order = studentOrderRepo.findByRazorpayOrderId(razorpayOrderId);
        order.setOrderStatus("payment complete");
        return studentOrderRepo.save(order);
    }
}
