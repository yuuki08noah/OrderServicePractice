package kr.co.ordermanagement.infrastructure;

import kr.co.ordermanagement.domain.exception.EntityNotFoundException;
import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.order.State;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListOrderRepository implements OrderRepository {
  private List<Order> orders = new CopyOnWriteArrayList<>();

  private AtomicLong sequence = new AtomicLong(1L);

  @Override
  public Order add(Order order) {
    order.setId(sequence.getAndAdd(1L));
    orders.add(order);
    return order;
  }

  @Override
  public Order findById(Long orderId) {
    return orders.stream()
            .filter(order -> order.sameId(orderId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    // filter: Lambda
  }

  @Override
  public List<Order> findByState(State state) {
    return orders.stream()
            .filter(order -> order.sameState(state))
            .toList();
  }
}