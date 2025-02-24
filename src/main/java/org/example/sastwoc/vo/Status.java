package org.example.sastwoc.vo;

public enum Status {
   SUCCESS(200, "成功！"),
   FAIL(400, "失败！"),
   NOT_FOUND(404, "资源未找到！"),
   SYSTEM_ERROR(500, "服务器内部错误！"),
   USER_ACCESS_NOT_ALLOWED(10001,"用户无权限"),
   USER_NOT_EXIST(10002,"用户不存在"),
   USER_EXIST(10003,"用户名已存在");



   private final int code;
   private final String msg;

   Status(int code, String msg) {
      this.code = code;
      this.msg = msg;
   }

   public int getCode() {
      return code;
   }

   public String getMsg() {
      return msg;
   }

   // 根据状态码获取对应的枚举实例
   public static Status fromCode(int code) {
      for (Status status : Status.values()) {
         if (status.getCode() == code) {
            return status;
         }
      }
      throw new IllegalArgumentException("未知的状态码: " + code);
   }

   @Override
   public String toString() {
      return String.format("Code: %d, Message: %s", code, msg);
   }
}
