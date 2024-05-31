package wanted.market.api.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wanted.market.api.model.dto.member.CustomUserInfoDto;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;


@Slf4j
@Component
public class JwtTokenProvider {

    private final long expirationTime;
    private final SecretKey cachedSecretKey;

    public JwtTokenProvider(@Value("${jwt.secret-plain}") String secretKey,
                            @Value("${jwt.expires-time}") long expirationTime) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        cachedSecretKey = Keys.hmacShaKeyFor(decode);
        this.expirationTime = expirationTime;
    }

    /**
     * accessToken 생성
     * @param member
     * @return
     */
    public String createAccessToken(CustomUserInfoDto member) {
        return generateToken(member, expirationTime);
    }

    /**
     * 유저 정보를 가지고 토큰 정보를 반환하는 메서드
     * @param expirationTime
     * @return
     */
    public String generateToken(CustomUserInfoDto member, long expirationTime) {

        Claims claims = Jwts.claims();
        claims.put("memberId", member.getMemberId());
        claims.put("email", member.getEmail());
        claims.put("nickname", member.getNickname());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(cachedSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getMemberId(String token) {
        return parseClaims(token).get("memberId", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(cachedSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token.", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
        }
        return false;
    }

    /**
     * accessToken을 복호화하는 메서드
     * @param accessToken
     * @return
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(cachedSecretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
