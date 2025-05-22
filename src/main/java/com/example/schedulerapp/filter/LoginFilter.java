package com.example.schedulerapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/api/users/signup", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        log.info("로그인 필터 로직 실행");

        // WHITE LIST 에 포함되지 않은 경우 해당 로직 수행
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            log.info("로그인에 성공했습니다.");
        }

        // 1번 경우: WHITE LIST 에 등록된 URL 요청 > filterChain.doFilter() 호출
        // 2번 경우: WHITE LIST 가 아닌 경우 > 위 필터로직 통과 후 filterChain.doFilter() 다음 필터 or Servlet 호출
        // filterChain: 다음 필터가 없으면 Servlet -> Controller, 다음 필터가 있으면 다음 Filter 호출
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
