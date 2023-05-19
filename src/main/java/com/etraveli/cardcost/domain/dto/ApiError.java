package com.etraveli.cardcost.domain.dto;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

public class ApiError implements Serializable {
  private String desc;
  private HttpStatus httpCode;
  private String code;
  
  public ApiError() {
   
  }
  
  public ApiError(String desc, HttpStatus httpCode, String code) {
    this.desc = desc;
    this.httpCode = httpCode;
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }
  public void setDesc(String desc) {
    this.desc = desc;
  }
  public HttpStatus getHttpCode() {
    return httpCode;
  }
  public void setHttpCode(HttpStatus httpCode) {
    this.httpCode = httpCode;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

}
