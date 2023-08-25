package com.driver;

import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order>orderDb=new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerDb=new HashMap<>();

    HashMap<String,String> orderPartnerDb=new HashMap<>();
    HashMap<String,List<String>>partnerOrderDb=new HashMap<>();

    public void addOrder(Order order) {
        String id=order.getId();
        if(orderDb.containsKey(id)) return;
        orderDb.put(id,order);
    }

    public void addPartner(String partnerId, DeliveryPartner deliveryPartner) {
        if(deliveryPartnerDb.containsKey(partnerId)) return;
        deliveryPartnerDb.put(partnerId,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {

        if(partnerOrderDb.containsKey(partnerId)) return;
        orderPartnerDb.put(orderId,partnerId);
       if(orderDb.containsKey(orderId) && deliveryPartnerDb.containsKey(partnerId))
       {
           List<String> orderList=new ArrayList<>();

           if(partnerOrderDb.containsKey(partnerId))
           {
               orderList=partnerOrderDb.get(partnerId);
           }
           orderList.add(orderId);
           partnerOrderDb.put(partnerId,orderList);
           deliveryPartnerDb.get(partnerId).setNumberOfOrders(orderList.size());

       }

    }

    public Order getOrderById(String orderId) {
            return orderDb.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerDb.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
       return partnerOrderDb.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orderList=new ArrayList<>();

        if(partnerOrderDb.containsKey(partnerId))
        {
            orderList=partnerOrderDb.get(partnerId);
        }
        return orderList;
    }

    public List<String> getAllOrders() {

        return new ArrayList<>(orderDb.keySet());
    }

    public Integer getCountOfUnassignedOrders() {

        int cnt=0;

        for(String key:orderDb.keySet())
        {
            if(!orderPartnerDb.containsKey(key))
            {
                cnt++;
            }
        }
        return cnt;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {

        Integer cnt=0;
        for (String order :partnerOrderDb.get(partnerId))
        {
            if(orderDb.get(order).getDeliveryTime()>time)
            {
                cnt++;
            }
        }
        return cnt;

    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orderList=partnerOrderDb.get(partnerId);

        //sort based on time
        Collections.sort(orderList,(a,b)->{
            return orderDb.get(a).getDeliveryTime()-orderDb.get(b).getDeliveryTime();
        });

        String orderId= orderList.get(orderList.size()-1);
        return orderDb.get(orderId).getDeliveryTime();

    }

    public void deletePartnerById(String partnerId) {

        if(deliveryPartnerDb.containsKey(partnerId))
        {
            for(String orderId:orderPartnerDb.keySet())
            {
                if(orderPartnerDb.get(orderId).equals(partnerId))
                {
                    orderPartnerDb.remove(orderId);
                }
            }

            partnerOrderDb.remove(partnerId);
            deliveryPartnerDb.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId) {
        if(orderPartnerDb.containsKey(orderId))
        {
            String partnerId=orderPartnerDb.get(orderId);
            orderPartnerDb.remove(orderId);

           List<String> olist=partnerOrderDb.get(partnerId);
           olist.remove(orderId);
           partnerOrderDb.put(partnerId,olist);
        }
        orderDb.remove(orderId);
    }
}
