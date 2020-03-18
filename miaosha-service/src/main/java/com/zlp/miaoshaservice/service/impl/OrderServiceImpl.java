package com.zlp.miaoshaservice.service.impl;

import com.zlp.miaoshadao.mapper.StockOrderMapper;
import com.zlp.miaoshadao.model.Stock;
import com.zlp.miaoshadao.model.StockOrder;
import com.zlp.miaoshaservice.service.OrderService;
import com.zlp.miaoshaservice.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private StockOrderMapper stockOrderMapper;

    @Override
    public int createWrongOrder(int sid) {
        //校验存
        Stock stock = checkStock(sid);
        //减库存
        saleStock(stock);
        //创建订单
        int id = createOrder(stock);
        return id;
    }

    @Override
    public int createOptimisticOrder(int sid) {
        //校验库存
        Stock stock = checkStock(sid);
        //乐观锁更新库存
        saleStockOptimistic(stock);
        //创建订单
        int id = createOrder(stock);
        return stock.getCount() - (stock.getSale()+1);
    }

    private void saleStockOptimistic(Stock stock) {
        LOGGER.info("查询数据库，尝试更新库存");
        int count = stockService.updateStockByOptimistic(stock);
        if(count == 0){
            throw new RuntimeException("并发更新库存失败，version不匹配");
        }
    }

    private int createOrder(Stock stock) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setSid(stock.getId());
        stockOrder.setName(stock.getName());
        return stockOrderMapper.insertSelective(stockOrder);
    }

    private void saleStock(Stock stock) {
        stock.setSale(stock.getSale()+1);
        stockService.updateStockById(stock);
    }

    private Stock checkStock(int sid) {
        Stock stock = stockService.getStockById(sid);
        if(stock.getSale().equals(stock.getCount())){
            throw new RuntimeException("库存不足");
        }
        return stock;
    }
}
