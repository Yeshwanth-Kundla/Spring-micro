package com.example.order_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.order_service.dto.ProductDto;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;

@Service
public class OrderService {
    
    public final OrderRepository orderRepository;
    public final RestTemplate restTemplate;

    
    public OrderService(OrderRepository orderRepository,
                        RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Order placeOrder(Order order){
        String productServiceUrl="http://localhost:8082/api/products/" + order.getProductId();

        ProductDto product=restTemplate.getForObject(productServiceUrl, ProductDto.class);

        if(product==null){
            throw new RuntimeException("Product not found");
        }
        return orderRepository.save(order);
    }
}
