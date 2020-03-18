package com.zlp.miaoshaservice.service.impl;

import com.zlp.miaoshadao.mapper.StockMapper;
import com.zlp.miaoshadao.model.Stock;
import com.zlp.miaoshaservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public Stock getStockById(int sid) {
        return stockMapper.selectByPrimaryKey(sid);
    }

    @Override
    public int updateStockById(Stock stock) {
        return stockMapper.updateByPrimaryKeySelective(stock);
    }
}
