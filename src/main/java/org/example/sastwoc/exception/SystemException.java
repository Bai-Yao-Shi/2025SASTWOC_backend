package org.example.sastwoc.exception;


import org.example.sastwoc.vo.Status;

/**
 * 自定义异常
 */
public class SystemException extends RuntimeException {
   private final int code;

   public SystemException(int code, String msg) {
      super(msg);
      this.code = code;
   }

   public SystemException(Status status) {
      super(status.getMsg());
      this.code = status.getCode();
   }

   public int getCode() {
      return code;
   }
}