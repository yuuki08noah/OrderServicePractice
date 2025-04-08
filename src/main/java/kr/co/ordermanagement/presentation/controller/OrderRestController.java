package kr.co.ordermanagement.presentation.controller;

import kr.co.ordermanagement.application.SimpleOrderService;
import kr.co.ordermanagement.presentation.dto.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController {

  private final SimpleOrderService simpleOrderService;

  @Autowired
  public OrderRestController(SimpleOrderService simpleOrderService) {
    this.simpleOrderService = simpleOrderService;
  }

  //상품 주문 API
  @PostMapping("/orders")
  public OrderResponseDto createOrder(
          @RequestBody List<OrderProductRequestDto> orderProductRequestDtos
  ) {
    OrderResponseDto orderResponseDto = simpleOrderService.createOrder(orderProductRequestDtos);
    return orderResponseDto;
  }


  //주문 상태 강제 변경 API
  @GetMapping("/orders/{orderId}")
  public OrderResponseDto getOrderById(@PathVariable Long orderId) {
    OrderResponseDto orderResponseDto = simpleOrderService.findById(orderId);
    return orderResponseDto;
  }

  //주문 번호로 조회 API

  //주문 상태로 조회 API

  //주문 취소 API


}
