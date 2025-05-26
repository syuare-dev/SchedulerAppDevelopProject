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

    /**
     * 화이트 리스트에 포함된 URL 목록: 리스트에 포함된 URL 은 로그인 상관없이 접근 가능
     */
    private static final String[] WHITE_LIST = {"/", "/api/users/signup", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        log.info("로그인 필터 로직 실행");

        /**
         * WHITE LIST 에 포함되지 않은 경우 로그인 로직 수행
         * Session 이 존재할 경우: 존재하는 Session 값을 가져오기
         * Session 이 존재하지 않을 경우: 401 에러 반환 + 로직 수행 종료
         */
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            log.info("로그인에 성공했습니다.");
        }

        /**
         * 1번 경우: WHITE LIST 에 등록된 URL 요청 > filterChain.doFilter() 호출
         * 2번 경우: WHITE LIST 가 아닌 경우 > 위 필터로직 통과 후 filterChain.doFilter() 다음 필터 or Servlet 호출
         * filterChain: 다음 필터가 없으면 Servlet -> Controller, 다음 필터가 있으면 다음 Filter 호출
         */
        filterChain.doFilter(servletRequest, servletResponse);

    }

    /**
     * 요청 URI > 화이트리스트 포함 여부 확인
     * @param requestURI 요청 URI
     * @return 화이트 리스트에 포함 O true, 포함 X false
     */

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
