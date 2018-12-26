package com.yuetsao.order_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.yuetsao.order_service.domain.ProductOrder;
import com.yuetsao.order_service.service.ProductClient;
import com.yuetsao.order_service.service.ProductOrderService;
import com.yuetsao.order_service.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Override
    public ProductOrder save(int userId, int productId) {

        String response = productClient.findById(productId);
        JsonNode jsonNode = JsonUtils.str2JsonNode(response);


//        final ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
//        System.out.println("----->"+serviceInstance.getPort());
//        String url = String.format("http://%s:%s/api/v1/product/findById?id="+productId, serviceInstance.getHost(), serviceInstance.getPort());
//        RestTemplate restTemplate = new RestTemplate();
        //获取商品信息
        //Map<String, Object> productMap = restTemplate.getForObject(url,Map.class);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setOutTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(jsonNode.get("name").toString());
        productOrder.setPrice(Integer.valueOf(jsonNode.get("price").toString()));
        return productOrder;
    }
}
