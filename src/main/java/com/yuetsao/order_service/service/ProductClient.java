package com.yuetsao.order_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品服务客户端
 */
@FeignClient(name="product-service")
public interface ProductClient  {

    @RequestMapping("/api/v1/product/findById")
    String findById(@RequestParam(value="id") int id);

}
