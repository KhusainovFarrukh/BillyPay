package kh.farrukh.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    public static final String KEY_PERMISSIONS = "permissions";

    private Algorithm accessTokenAlgorithm;
    private Algorithm refreshTokenAlgorithm;

    @Override
    public void afterPropertiesSet() {
        accessTokenAlgorithm = Algorithm.HMAC256(JwtConstants.SECRET);
        refreshTokenAlgorithm = Algorithm.HMAC384(JwtConstants.SECRET);
    }

    public TokenResponseDTO generateTokens(String phoneNumber, List<Permission> permissions) {
        ZonedDateTime accessExpireDate = ZonedDateTime.now().plusSeconds(JwtConstants.ACCESS_TOKEN_VALIDITY_IN_SECONDS);
        ZonedDateTime refreshExpireDate = ZonedDateTime.now().plusSeconds(JwtConstants.REFRESH_TOKEN_VALIDITY_IN_SECONDS);

        return new TokenResponseDTO(
                permissions,
                createAccessToken(phoneNumber, permissions, accessExpireDate),
                createRefreshToken(phoneNumber, permissions, refreshExpireDate),
                accessExpireDate,
                refreshExpireDate
        );
    }

    public TokenResponseDTO generateAccessToken(String phoneNumber, List<Permission> permissions) {
        ZonedDateTime accessExpireDate = ZonedDateTime.now().plusSeconds(JwtConstants.ACCESS_TOKEN_VALIDITY_IN_SECONDS);
        return new TokenResponseDTO(
                null,
                createAccessToken(phoneNumber, permissions, accessExpireDate),
                null,
                accessExpireDate,
                null
        );
    }

    public DecodedJWT validateToken(String authHeader, boolean isRefresh) {
        Algorithm algorithm;
        if (isRefresh) {
            algorithm = refreshTokenAlgorithm;
        } else {
            algorithm = accessTokenAlgorithm;
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            return jwtVerifier.verify(token);
        } else {
            // TODO: 11/21/22 custom exception
//            throw new MissingTokenException();
            throw new RuntimeException("Missing token");
        }
    }

    public String createAccessToken(
            String phoneNumber,
            List<Permission> permissions,
            ZonedDateTime expireDate
    ) {
        return createToken(phoneNumber, permissions, expireDate, accessTokenAlgorithm);
    }

    public String createRefreshToken(
            String phoneNumber,
            List<Permission> permissions,
            ZonedDateTime expireDate
    ) {
        return createToken(phoneNumber, permissions, expireDate, refreshTokenAlgorithm);
    }

    private String createToken(
            String phoneNumber,
            List<Permission> permissions,
            ZonedDateTime expireDate,
            Algorithm algorithm
    ) {
        return JWT.create()
                .withSubject(phoneNumber)
                .withExpiresAt(Date.from(expireDate.toInstant()))
                .withClaim(KEY_PERMISSIONS, permissions)
                .sign(algorithm);
    }
}