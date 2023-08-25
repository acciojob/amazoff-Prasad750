package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {


   OrderRepository orderRepository=new OrderRepository();
    public void addOrder(Order order) {
       orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        orderRepository.addPartner(partnerId,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return  orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {

        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String [] arr=time.split(":");
        int t= Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
        return orderRepository. getOrdersLeftAfterGivenTimeByPartnerId(t,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time=orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        int HH=time/60;
        int MM=time%60;

        StringBuilder sb=new StringBuilder();
        if(HH<10)
        {
            sb.append("0");
        }
        sb.append(HH);

        sb.append(":");

        if(MM<10)
        {
            sb.append(0);
        }
        sb.append(MM);

        return sb.toString();


    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
