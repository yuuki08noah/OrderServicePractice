package kr.co.ordermanagement.presentation.controller;

import kr.co.ordermanagement.application.SimpleOrderService;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.presentation.dto.ChangeOrderStateRequestDto;
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
  @PatchMapping("/orders/{orderId}")
  // 보통 도메인 전체를 바꿀 때 update~(), 도메인의 일부를 바꿀 때는 change~()를 사용
  public OrderResponseDto changeOrderById(
          @PathVariable Long orderId,
          @RequestBody ChangeOrderStateRequestDto changeOrderStateRequestDto
  ) {
    OrderResponseDto orderResponseDto = simpleOrderService.changeState(orderId, changeOrderStateRequestDto);
    return orderResponseDto;
  }

  //주문 번호로 조회 API
  @GetMapping("/orders/{orderId}")
  public OrderResponseDto getOrderById(@PathVariable Long orderId) {
    OrderResponseDto orderResponseDto = simpleOrderService.findById(orderId);
    return orderResponseDto;
  }

  //주문 상태로 조회 API
  @GetMapping("/orders")
  public List<OrderResponseDto> getOrdersByState(@RequestParam State state) {
    List<OrderResponseDto> orderResponseDtos = simpleOrderService.findByState(state);
    return orderResponseDtos;
  }

  //주문 취소 API
  @PatchMapping("/orders/{orderId}/cancel")
  public OrderResponseDto cancelOrderById(@PathVariable Long orderId) {
    OrderResponseDto orderResponseDto = simpleOrderService.cancelById(orderId);
    return orderResponseDto;
  }

}
