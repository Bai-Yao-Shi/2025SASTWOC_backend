package org.example.sastwoc.handle;

import org.example.sastwoc.vo.Result;
import org.example.sastwoc.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
   @ExceptionHandler(SystemException.class)
   public Result doSystemException(SystemException e) {
      log.info("出现系统异常!", e);

      return Result.fail(e.getCode(), e.getMessage());
   }

   @ExceptionHandler(Exception.class)
   public Result exceptionHandler(Exception e) {
      log.error("出现未知异常！", e);

      return Result.fail(500, "网络繁忙，请稍后再试！");
   }
}