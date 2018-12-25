package com.yuetsao.order_service.service.impl;

import com.yuetsao.order_service.domain.ProductOrder;
import com.yuetsao.order_service.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ProductOrder save(int userId, int productId) {
        //获取商品信息
        Object obj = restTemplate.getForObject("http://product-service/api/v1/product/findById?id="+productId,Object.class);
        System.out.println(obj);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setOutTradeNo(UUID.randomUUID().toString());
        return productOrder;
    }
}
