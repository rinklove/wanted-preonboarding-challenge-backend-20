package wanted.market.api.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("인증되지 않은 요청입니다.", authException);

        // 응답 헤더 설정
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized

        // 응답 바디 작성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "인증되지 않은 요청입니다.");
        responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseBody.put("occurrenceTime", LocalDateTime.now());

        // 응답 바디를 JSON 형식으로 변환하여 응답
        String responseJson = objectMapper.writeValueAsString(responseBody);
        response.getWriter().write(responseJson);
    }
}
