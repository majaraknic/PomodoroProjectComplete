package eu.execom.pomodoro.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Token";
    public static final long EXPIRATION_TIME = 600000000;
}