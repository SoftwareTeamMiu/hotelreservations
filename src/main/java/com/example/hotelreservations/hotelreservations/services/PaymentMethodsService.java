package com.example.hotelreservations.hotelreservations.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.PaymentMethods;
import com.example.hotelreservations.hotelreservations.repositories.PaymentMethodsRepository;

import java.util.List;

@Service
public class PaymentMethodsService {
    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    public List<PaymentMethods> getAllPaymentMethods(){return paymentMethodsRepository.findAll();}

    public PaymentMethods getPaymentMethodByID(Integer id){return paymentMethodsRepository.findById(id).orElse(null);}

    public PaymentMethods createPaymentMethod(PaymentMethods paymentMethods){return paymentMethodsRepository.save(paymentMethods);}

    public void deletePaymentMethod(Integer id){paymentMethodsRepository.deleteById(id);}
}
