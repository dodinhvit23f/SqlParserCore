package com.sotatek.sql.parser.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SqlOperators {
  OP_GREATER_THAN_EQUALS(">="),
  OP_MINOR_THAN_EQUALS("<="),
  OP_NOT_EQUALS_STANDARD("<>"),
  OP_NOT_EQUALS_THAN("!="),
  OP_CONCAT("||"),
  OP_DOUBLE_AND("&&"),
  OP_CONTAINS("&>"),
  OP_CONTAINED_BY("<&");

  String operator;

  public String getOperator() {
    return operator;
  }
}
