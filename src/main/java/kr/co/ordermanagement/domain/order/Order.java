package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.product.Product;

import java.util.List;

public class Order {
  private Long id;
  private List<Product> orderedProducts;
  private Integer totalPrice;
  private String state;

  public Order(List<Product> orderedProducts) {
    this.orderedProducts = orderedProducts;
    this.totalPrice = calculateTotalPrice(orderedProducts);
    this.state = "CREATE";
  }

  private Integer calculateTotalPrice(List<Product> orderedProducts) {
    return orderedProducts
            .stream()
            .mapToInt(orderedProduct -> orderedProduct.getPrice() * orderedProduct.getAmount())
            .sum();
  }

  public void setId(long andAdd) {
    this.id = andAdd;
  }

  public List<Product> getOrderedProducts() {
    return this.orderedProducts;
  }

  public Long getId() {
    return this.id;
  }

  public Integer getTotalPrice() {
    return this.totalPrice;
  }

  public String getState() {
    return this.state;
  }

  public Boolean sameId(Long id) {
    return this.id.equals(id);
  }

  public void changeStateForce(String state) {
    this.state = state;
  }

  public boolean sameState(String state) {
    return this.state.equals(state);
  }
}
