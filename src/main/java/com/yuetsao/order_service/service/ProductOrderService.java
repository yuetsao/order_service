package com.yuetsao.order_service.service;

import com.yuetsao.order_service.domain.ProductOrder;

public interface ProductOrderService {
    ProductOrder save(int userId, int productId);
}
