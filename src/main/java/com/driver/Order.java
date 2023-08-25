package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        String [] arr=deliveryTime.split(":");
        int HH=Integer.parseInt(arr[0]);
        int MM=Integer.parseInt(arr[1]);

        this.deliveryTime=HH*60 + MM;
        this.id=id;

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
