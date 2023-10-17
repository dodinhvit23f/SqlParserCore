package com.sotatek.sql.parser.constant;

public enum QueryTag {
  SELECT,
  INSERT,
  UPDATE,
  DELETE,
  EXEC;

  public static boolean validText(String text) {
    switch (text) {
      case SqlKeyWord.SELECT:
      case SqlKeyWord.INSERT:
      case SqlKeyWord.UPDATE:
      case SqlKeyWord.DELETE:
      case SqlKeyWord.EXEC:
        return Boolean.TRUE;
      default:
        return Boolean.FALSE;
    }
  }
}
