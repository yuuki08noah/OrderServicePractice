package kr.co.ordermanagement.domain.exception;

public class NotEnoughAmountException extends RuntimeException {
  public NotEnoughAmountException(String message) {
    super(message); // 부모 클래스의 생성자를 호출함
  }
}
