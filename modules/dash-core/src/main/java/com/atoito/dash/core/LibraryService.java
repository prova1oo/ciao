package com.atoito.dash.core;

/**
 * Library service does this...
 */
public class LibraryService {

  /**
   * Operate does this...
   *
   * @param input
   * @return the input!
   */
  public String operate(String input) {
    if (input == null) {
      return "null value";
    }
    return "%s!".formatted(input);
  }
}
