package org.example.sastwoc.interceptor;

import com.google.common.collect.Sets;
import org.example.sastwoc.config.CacheManager;
import org.example.sastwoc.entity.User;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.mapper.PersonMapper;
import org.example.sastwoc.utils.JwtUtils;
import org.example.sastwoc.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class HttpInterceptor implements HandlerInterceptor {
    @Autowired
    private PersonMapper personMapper;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();  // 创建 AntPathMatcher 实例

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 使用 findMatchingRoles 查找请求 URI 匹配的角色集合
        Set<String> uriRoles = findMatchingRoles(requestURI);

        // 如果没有匹配的角色集合，直接放行
        if (CollectionUtils.isEmpty(uriRoles)) {
            return true;
        }

        // 解析 JWT token 获取用户信息
        User user = JwtUtils.resolveJwt(request.getHeader("token"));
        String userCode = user.getUser_code();

        // 获取用户角色
        Set<String> userRoles = getUserRoles(userCode);

        // 判断用户是否有权限访问该 URI
        if (CollectionUtils.isEmpty(uriRoles) || Sets.intersection(uriRoles, userRoles).isEmpty()) {
            System.out.println("Required Roles: " + uriRoles);
            System.out.println("User Roles: " + userRoles);
            throw new SystemException(Status.USER_ACCESS_NOT_ALLOWED);
        }

        return true;
    }

    /**
     * 查找与请求 URI 匹配的角色集合
     */
    private Set<String> findMatchingRoles(String requestURI) {
        for (Map.Entry<String, Set<String>> entry : CacheManager.URI_ROLE_MAP.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), requestURI)) {  // 使用 AntPathMatcher 进行路径匹配
                return entry.getValue();
            }
        }
        return null;  // 没有匹配的角色集合
    }

    /**
     * 获取用户角色信息
     */
    private Set<String> getUserRoles(String userCode) {
        Set<String> userRoles = new HashSet<>();

        Integer isCaptain = personMapper.getIsCaptainByUserCode(userCode);
        Integer isPrimaryAdmin = personMapper.getIsPrimaryAdminByUserCode(userCode);
        Integer isSecondaryAdmin = personMapper.getIsSecondaryAdminByUserCode(userCode);
        Integer isJudge = personMapper.getIsJudgeByUserCode(userCode);
        Integer isInstructor = personMapper.getIsInstructorByUserCode(userCode);
        Integer teamId = personMapper.getTeamIdByUserCode(userCode);

        if (isCaptain != null && isCaptain.equals(1)) {
            userRoles.add("captain");
        }
        if (isPrimaryAdmin != null && isPrimaryAdmin.equals(1)) {
            userRoles.add("primary_admin");
        }
        if (isSecondaryAdmin != null && isSecondaryAdmin.equals(1)) {
            userRoles.add("secondary_admin");
        }
        if (isJudge != null && isJudge.equals(1)) {
            userRoles.add("judge");
        }
        if (isInstructor != null && isInstructor.equals(1)) {
            userRoles.add("instructor");
        }
        if (teamId != null) {
            userRoles.add("team_id:" + teamId);
        }

        return userRoles;
    }
}
