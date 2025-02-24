package org.example.sastwoc.config;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CacheManager {
    //用户访问接口URI需要的身份
    public static Map<String, Set<String> > URI_ROLE_MAP=new HashMap<>();
    //用户具有的身份
    //public static Map<String,Set<String>> USER_ROLE_MAP=new HashMap<>();

    static {
        URI_ROLE_MAP.put("/**", Sets.newHashSet("primary_admin"));

        URI_ROLE_MAP.put("/academy/**", Sets.newHashSet("primary_admin","secondary_admin"));


        URI_ROLE_MAP.put("/judge", Sets.newHashSet("primary_admin","secondary_admin","judge"));


        URI_ROLE_MAP.put("/admin/**", Sets.newHashSet("primary_admin"));



        URI_ROLE_MAP.put("/captain", Sets.newHashSet("captain"));
        URI_ROLE_MAP.put("/captain/team", Sets.newHashSet("captain"));
        URI_ROLE_MAP.put("/captain/**", Sets.newHashSet("captain","secondary_admin","primary_admin"));


        URI_ROLE_MAP.put("/competitions/**", Sets.newHashSet("primary_admin","secondary_admin","captain"));


    }
}
