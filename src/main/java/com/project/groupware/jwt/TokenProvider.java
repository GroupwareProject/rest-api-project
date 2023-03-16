package com.project.groupware.jwt;

import com.project.groupware.exception.TokenException;
import com.project.groupware.member.dto.TokenDTO;
import com.project.groupware.member.entity.Member;
import com.project.groupware.member.entity.MemberRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;	// 30분(ms 단위)

    private final UserDetailsService userDetailsService;

    private final Key key;		// java.security.Key로 임포트 할 것

    public TokenProvider(@Value("${jwt.secret}")String secretKey,
                         UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /* 1. 토큰 생성 메소드 */
    public TokenDTO generateTokenDTO(Member member) {

        log.info("[TokenProvider] generateTokenDTO Start ===============================");
        List<String> roles = new ArrayList<>();
        for(MemberRole memberRole : member.getMemberRole()) {
            roles.add(memberRole.getAuthority().getAuthorityName());
        }

        log.info("[TokenProvider] authorities {}", roles);

        /* 1. 회원 아이디를 "sub"이라는 클레임으로 토큰에 추가 */
        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getMemberCode()));

        /* 2. 회원의 권한들을 "auth"라는 클레임으로 토큰에 추가 */
        claims.put(AUTHORITIES_KEY, roles);

        long now = System.currentTimeMillis();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setClaims(claims)

                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        log.info("[TokenProvider] generateTokenDTO End ===============================");

        return new TokenDTO(BEARER_TYPE, member.getMemberName(), accessToken,
                accessTokenExpiresIn.getTime());
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()					// payload의 Claims 추출
                .getSubject();				// Claim중에 등록 클레임에 해당하는 sub값 추출(회원 아이디)
    }

    /* 3. AccessToken으로 인증 객체 추출(이 클래스의 5번과 2번에 해당하는 메소드를 사용) */
    public Authentication getAuthentication(String token) {

        log.info("[TokenProvider] getAuthentication Start ===============================");

        /* 토큰에서 claim들 추출(토큰 복호화) */
        Claims claims = parseClaims(token);		// 아래 5번에서 만든 메소드

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        /* 클레임에서 권한 정보 가져오기 */
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());
        log.info("[TokenProvider] authorities {}", authorities);

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

        log.info("[TokenProvider] getAuthentication End ===============================");

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /* 4. 토큰 유효성 검사 */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new TokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

    /* 5. AccessToken에서 클레임 추출하는 메소드 */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); 			// 토큰이 만료되어 예외가 발생하더라도 클레임 값들은 뽑을 수 있다.
        }
    }
}
