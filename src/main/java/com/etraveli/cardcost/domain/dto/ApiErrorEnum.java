package com.etraveli.cardcost.domain.dto;

import org.springframework.http.HttpStatus;


public enum ApiErrorEnum {
  UNKNOWN_HOST_EXCEPTION("Host could not be reached",HttpStatus.GATEWAY_TIMEOUT, "1-0001"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  API_VALIDATION_EXCEPTION("Api validation error",HttpStatus.NOT_ACCEPTABLE, "1-0002"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  API_RESPONSE_EXCEPTION("Api response error",HttpStatus.PRECONDITION_FAILED, "1-0003"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  BAD_REQUEST_EXCEPTION("Api response error",HttpStatus.BAD_REQUEST, "1-0004"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  USER_ALREADY_EXISTS("Api response error",HttpStatus.CONFLICT, "1-0004"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  API_NOT_SUPPORTED_EXCEPTION("Api response error",HttpStatus.PRECONDITION_FAILED, "1-0003"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  
  
  CONVERSION_FAILED_ERROR("Api conversion error",HttpStatus.BAD_REQUEST, "1-0003"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  
  NOT_FOUND_ERROR("No entityt found",HttpStatus.NOT_FOUND, "1-0003"){
    @Override
    public ApiError create() {
      return new ApiError(this.getDesc(), this.getHttpCode(), this.getCode());
    }
  },
  ;
  private String desc;
  private HttpStatus httpCode;
  private String code;
  private ApiErrorEnum(String desc, HttpStatus httpCode, String code) {
    this.desc = desc;
    this.httpCode = httpCode;
    this.code = code;
  }
  public String getDesc() {
    return desc;
  }
  public HttpStatus getHttpCode() {
    return httpCode;
  }
  public String getCode() {
    return code;
  }
  
  public abstract ApiError create();

}
