package kh.farrukh.common.security;

//@Getter
//@Setter
//@Component
public class JwtConstants {

    // TODO: 11/21/22 migrate to using yaml files

    //    @Value("${jwt.secret}")
    public static final String SECRET = "secret";

    //    @Value("${jwt.access-token-validity-in-seconds}")
    public static final Long ACCESS_TOKEN_VALIDITY_IN_SECONDS = 1800L;

    //    @Value("${jwt.refresh-token-validity-in-seconds}")
    public static final Long REFRESH_TOKEN_VALIDITY_IN_SECONDS = 604800L;
}
