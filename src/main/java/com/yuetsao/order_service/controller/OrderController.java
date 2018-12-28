package com.yuetsao.order_service.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuetsao.order_service.service.ProductOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id")int userId, @RequestParam("product_id") int productId, HttpServletRequest request) {
        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");
        System.out.println("token="+token);
        System.out.println("cookie="+cookie);
        Map<String, Object> result = new HashMap<>();
        result.put("code" , -1);
        result.put("msg", "下单成功");
        result.put("data", productOrderService.save(userId,productId));
        return result;
    }

    private Object saveOrderFail(int userId, int productId, HttpServletRequest request) {
        //监控报警
        String saveOrderKey = "save-order";
        String sendValue = stringRedisTemplate.opsForValue().get(saveOrderKey);
        String ip = request.getRemoteAddr();
        new Thread(()->{
            if(StringUtils.isBlank(sendValue)) {
                System.out.println("紧急短信，用户下单失败"+ip);
                //发送一个http请求，调用短信服务
                stringRedisTemplate.opsForValue().set(saveOrderKey, "saveOrderFail", 20, TimeUnit.SECONDS);
            }else {
                System.out.println("已经发送短信，二十分钟内不发送");
            }
        }).start();
        Map<String, Object> result = new HashMap<>();
        result.put("code" , -1);
        result.put("msg", "目前下单人数过多，请稍后重试");
        result.put("data", null);
        return result;

    }


}
