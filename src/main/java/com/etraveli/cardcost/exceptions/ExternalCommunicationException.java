package com.etraveli.cardcost.exceptions;

public class ExternalCommunicationException extends RuntimeException{

  public ExternalCommunicationException() {
    super();
  }
  
  public ExternalCommunicationException(int value) {
    super(Integer.toString(value));
  }

  public ExternalCommunicationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ExternalCommunicationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExternalCommunicationException(String message) {
    super(message);
  }

  public ExternalCommunicationException(Throwable cause) {
    super(cause);
  }
  
}
