package kr.co.ordermanagement.presentation.dto;

import kr.co.ordermanagement.domain.order.State;

public class ChangeOrderStateRequestDto {
  private State state;

  public State getState() {
    return state;
  }
}
