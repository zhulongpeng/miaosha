package com.zlp.miaoshaweb.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.zlp.miaoshadao.mapper.StockOrderMapper;
import com.zlp.miaoshaservice.service.OrderService;
import com.zlp.miaoshaservice.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private StockService stockService;

    @Autowired
    private OrderService orderService;

    RateLimiter rateLimiter = RateLimiter.create(100);

    @GetMapping("/createWrongOrder/{sid}")
    @ResponseBody
    public String createWrongOrder(
            @PathVariable int sid
    ){
        int id = 0;
        try {
            id = orderService.createWrongOrder(sid);
            LOGGER.info("创建订单id:[{}]", id);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception", e.getMessage());
        }
        return String.valueOf(id);
    }


    /**
     * 乐观锁更新库存+令牌桶限流
     */
    @GetMapping("/createOptimisticOrder/{sid}")
    @ResponseBody
    public String createOptimisticOrder(
            @PathVariable int sid
    ){
        if(!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
            LOGGER.warn("被限流次");
            return "购买失败，库存不足";
        }
        int num = 0;
        try {
            num = orderService.createOptimisticOrder(sid);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return String.format("购买成功，剩余库存为：%d",num);
    }
}
