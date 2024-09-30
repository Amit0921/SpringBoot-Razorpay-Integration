package com.tech.razorpay.controller;

import com.razorpay.RazorpayException;
import com.tech.razorpay.entity.StudentOrders;
import com.tech.razorpay.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String init(){
        return "index";
    }

    @PostMapping(value = "create-order", produces = "application/json")
    @ResponseBody
    public ResponseEntity<StudentOrders> createOrder(@RequestBody StudentOrders studentOrders) throws RazorpayException {
        StudentOrders createOrder = studentService.createOrder(studentOrders);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);

    }

    @PostMapping("/handle-payment-callback")
    public String handlePaymentCallback(@RequestParam Map<String,String> resPayLoad ){
        System.out.println("++++++++++" + resPayLoad);
        StudentOrders updateOrders = studentService.updateOrder(resPayLoad);
        return "success";
    }
}
