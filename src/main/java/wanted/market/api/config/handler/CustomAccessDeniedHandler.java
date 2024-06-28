package wanted.market.api.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.error("권한이 없습니다.", accessDeniedException);

        // 응답 헤더 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden

        // 응답 바디 작성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "권한이 없습니다.");
        responseBody.put("status", HttpServletResponse.SC_FORBIDDEN);
        responseBody.put("occurrenceTime", LocalDateTime.now());

        // 응답 바디를 JSON 형식으로 변환하여 응답
        String responseJson = objectMapper.writeValueAsString(responseBody);
        response.getWriter().write(responseJson);
    }
}
