package com.zlp.miaoshaservice.service;

import com.zlp.miaoshadao.model.Stock;

public interface StockService {

    Stock getStockById(int sid);

    int updateStockById(Stock stock);

    int updateStockByOptimistic(Stock stock);

}
