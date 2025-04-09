package kr.co.ordermanagement.presentation.dto;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.State;

import java.util.List;

public class OrderResponseDto {
  private Long id;
  private List<presentation.dto.ProductDto> orderProducts;
  private Integer totalPrice;
  // state는 enum으로 처리하는 것이 좋아보임
  private State state;

  public OrderResponseDto(Long id, List<presentation.dto.ProductDto> orderedProductDtos, Integer totalPrice, State state) {
    this.id = id;
    this.orderProducts = orderedProductDtos;
    this.totalPrice = totalPrice;
    this.state = state;
  }

  public static OrderResponseDto toDto(Order order) {
    List<presentation.dto.ProductDto> orderedProductDtos = order.getOrderedProducts()
            .stream()
            .map(orderedProduct -> presentation.dto.ProductDto.toDto(orderedProduct))
            .toList();
    OrderResponseDto orderResponseDto = new OrderResponseDto(
            order.getId(),
            orderedProductDtos,
            order.getTotalPrice(),
            order.getState()
    );
    return orderResponseDto;

  }
}