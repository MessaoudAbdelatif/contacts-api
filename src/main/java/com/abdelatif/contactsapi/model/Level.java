package com.abdelatif.contactsapi.model;

public enum Level {
  FUNDAMENTAL("Fundamental Awareness"),
  NOVICE("Novice"),
  INTERMEDIATE("Intermediate"),
  ADVANCED("Advanced"),
  EXPERT("Expert");

  private final String value;

  Level(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
