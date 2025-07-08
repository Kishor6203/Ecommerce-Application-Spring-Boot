package com.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.domain.OrderStatus;
import com.ecommerce.domain.PaymentStatus;
import com.ecommerce.model.Address;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	private final AddressRepository addressRepository;
	private final OrderItemRepository orderItemRepository;
	
	@Override
	public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
		
		if(!user.getAddresses().contains(shippingAddress)){
	        user.getAddresses().add(shippingAddress);
	    }
	    Address address=addressRepository.save(shippingAddress);

	    // brand 1 => 4 shirt
	    // brand 2 => 3 pants
	    // brand 3 => 1 watch

	    Map<Long, List<CartItem>> itemsBySeller=cart.getCartItems().stream()
	            .collect(Collectors.groupingBy(item->item.getProduct()
	                    .getSeller().getId()));

	    Set<Order> orders=new HashSet<>();
	    
	    for(Map.Entry<Long, List<CartItem>> entry:itemsBySeller.entrySet()){
	        Long sellerId=entry.getKey();
	        List<CartItem> items=entry.getValue();

	        int totalOrderPrice=items.stream().mapToInt(
	                CartItem::getSellingPrice
	        ).sum();
	        int totalItem=items.stream().mapToInt(CartItem::getQuantity).sum();

	        Order createdOrder=new Order();
	        createdOrder.setUser(user);
	        createdOrder.setSellerId(sellerId);
	        createdOrder.setTotalMrpPrice(totalOrderPrice);
	        createdOrder.setTotalSellingPrice(totalOrderPrice);
	        createdOrder.setTotalItem(totalItem);
	        createdOrder.setShippingAddress(address);
	        createdOrder.setOrderStatus(OrderStatus.PENDING);
	        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

	        Order savedOrder=orderRepository.save(createdOrder);
	        orders.add(savedOrder);

	        List<OrderItem> orderItems=new ArrayList<>();

	        for(CartItem item:items){
	            OrderItem orderItem=new OrderItem();
	            orderItem.setOrder(savedOrder);
	            orderItem.setMrpPrice(item.getMrpPrice());
	            orderItem.setProduct(item.getProduct());
	            orderItem.setQuantity(item.getQuantity());
	            orderItem.setSize(item.getSize());
	            orderItem.setUserId(item.getUserId());
	            orderItem.setSellingPrice(item.getSellingPrice());
	            
	            savedOrder.getOrderItems().add(orderItem);
	            
	            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
	            orderItems.add(savedOrderItem);

	        }
	    }

	    return orders;
	}
	
	@Override
	public Order findOrderByid(Long id) throws Exception {
		return orderRepository.findById(id).orElseThrow(()->new Exception("order not found...."));
	}
	
	@Override
	public List<Order> usersOrderHistory(Long userId) {
		return orderRepository.findByUserId(userId);
	}
	
	@Override
	public List<Order> sellersOrder(Long sellerId) {
		return orderRepository.findBySellerId(sellerId);
	}
	
	@Override
	public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
	    Order order=findOrderByid(orderId);
	    order.setOrderStatus(orderStatus);
	    return orderRepository.save(order);
	}

	@Override
	public Order cancelOrder(Long orderId, User user) throws Exception {
	    Order order=findOrderByid(orderId);

	    if(!user.getId().equals(order.getUser().getId())){
	        throw new Exception("you don't have access to this order");
	    }
	    order.setOrderStatus(OrderStatus.CANCELLED);
	    return orderRepository.save(order);
	}
	
	@Override
	public OrderItem getOrderItemById(Long id) throws Exception {
		return orderItemRepository.findById(id).orElseThrow(()->new Exception("order item not exixt...."));
	}
	
}
