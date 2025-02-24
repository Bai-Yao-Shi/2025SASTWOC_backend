package org.example.sastwoc.utils;

import org.example.sastwoc.entity.User;

/**
 * 利用ThreadLocal存储登录后的用户信息
 */
public class UserThreadLocal {
   private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

   private UserThreadLocal() {}

   public static void put(User user) {
      LOCAL.set(user);
   }
   public static User get() {
      return LOCAL.get();
   }
   public static void remove() {
      LOCAL.remove();
   }
}