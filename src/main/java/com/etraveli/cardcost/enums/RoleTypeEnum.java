package com.etraveli.cardcost.enums;

public enum RoleTypeEnum {
  BO("BO"),
  USER("USER")
  ;
  private String id;
  
  RoleTypeEnum(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
  
}
