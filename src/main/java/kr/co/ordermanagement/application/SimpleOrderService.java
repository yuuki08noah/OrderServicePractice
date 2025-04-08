package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleOrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public SimpleOrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public OrderResponseDto createOrder(List<OrderProductRequestDto> orderProductRequestDtos) {
        //OrderProductRequestDto의 상품번호(id)에 해당하는 상품이 재고가 있는지 확인 ->
        //if) 해당 상품이 없거나 재고가 없으면 예외발생
        //else) List<Product> 생성
        List<Product> orderedProducts = makeOrderedProducts(orderProductRequestDtos);

        //List<Product>로 해당 상품들의 상품 재고를 차감
        decreaseProductsAmount(orderedProducts);

        //차감된 상품 정보를 바탕으로 주문 생성
        Order order = new Order(orderedProducts);
        orderRepository.add(order);

        //생성된 주문을 OrderResponseDto로 변환하여 반환
        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
        return orderResponseDto;
    }

    public OrderResponseDto findById(Long id) {
      Order order = orderRepository.findById(id);
      OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
      return orderResponseDto;
    }

    private List<Product> makeOrderedProducts(List<OrderProductRequestDto> orderProductRequestDtos) {
        return orderProductRequestDtos
                .stream()
                .map(orderProductRequestDto -> {
                    //주문요청 안에 있는 각각의 Id로 제품 찾기
                    Long productId = orderProductRequestDto.getId();
                    Product product = productRepository.findById(productId);

                    //주문요청의 수량과 찾은 제품의 수량을 비교하여 구매가능한지 확인
                    Integer orderedAmount = orderProductRequestDto.getAmount();
                    product.checkEnoughAmount(orderedAmount);

                    //가능하도록 통과되면 product들로 주문 list 생성
                    return new Product(
                            productId,
                            product.getName(),
                            product.getPrice(),
                            orderedAmount
                    );
                }).toList();
    }

    private void decreaseProductsAmount(List<Product> orderedProducts) {
        orderedProducts.forEach(orderedProduct -> {
            Long productId = orderedProduct.getId();
            Product product = productRepository.findById(productId);

            Integer orderedAmount = orderedProduct.getAmount();
            product.decreaseAmount(orderedAmount);

            productRepository.update(product);
        });
    }

}


