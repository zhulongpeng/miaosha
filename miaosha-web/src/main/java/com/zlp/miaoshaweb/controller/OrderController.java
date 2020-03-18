package com.zlp.miaoshaweb.controller;

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

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private StockService stockService;

    @Autowired
    private OrderService orderService;

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

}
