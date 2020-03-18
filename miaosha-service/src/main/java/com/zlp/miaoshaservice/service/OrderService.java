package com.zlp.miaoshaservice.service;

public interface OrderService {

    int createWrongOrder(int sid);

    int createOptimisticOrder(int sid);

}
