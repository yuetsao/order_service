package com.yuetsao.order_service.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuetsao.order_service.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id")int userId, @RequestParam("product_id") int productId) {
        Map<String, Object> result = new HashMap<>();
        result.put("code" , -1);
        result.put("msg", "下单成功");
        result.put("data", productOrderService.save(userId,productId));
        return result;
    }

    private Object saveOrderFail(int userId, int productId) {
        Map<String, Object> result = new HashMap<>();
        result.put("code" , -1);
        result.put("msg", "目前下单人数过多，请稍后重试");
        result.put("data", null);
        return result;

    }


}
