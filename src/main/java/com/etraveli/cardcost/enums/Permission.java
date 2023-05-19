package com.etraveli.cardcost.enums;

public enum Permission {

  BO_READ("bo:read"), 
  BO_UPDATE("bo:update"), 
  BO_CREATE("bo:create"), 
  BO_DELETE("bo:delete"), 
  USER_READ("user:read"),

  ;

  Permission(String permission) {
    this.permission = permission;
  }

  private final String permission;

  public String getPermission() {
    return permission;
  }


}
