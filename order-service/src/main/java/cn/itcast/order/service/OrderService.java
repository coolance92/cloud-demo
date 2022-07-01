package cn.itcast.order.service;

import cn.itcast.order.client.UserServiceClient;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        String url = "http://user-service/user/" + order.getUserId();
        User user = restTemplate.getForObject(url, User.class);
        order.setUser(user);
        // 4.返回
        return order;
    }

    public Order queryOrderByIdFeign(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        User user = userServiceClient.findById(order.getUserId());
        order.setUser(user);
        // 4.返回
        return order;
    }
}
