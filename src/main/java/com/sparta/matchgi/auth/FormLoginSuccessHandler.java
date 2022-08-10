package com.sparta.matchgi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.auth.jwt.JwtTokenUtils;
import com.sparta.matchgi.dto.LoginResponseDto;
import com.sparta.matchgi.model.RefreshToken;
import com.sparta.matchgi.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";
    public static final String REFRESH_AUTH_HEADER = "Refresh_Authorization";

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        final ObjectMapper objectMapper = new ObjectMapper();

        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        final String refresh_token = JwtTokenUtils.generateJwtRefreshToken();

        refreshTokenRepository.save(new RefreshToken(userDetails.getUser(),refresh_token));

        LoginResponseDto loginResponseDto = new LoginResponseDto(TOKEN_TYPE + " " + token,TOKEN_TYPE + " " + refresh_token);
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
        response.addHeader(REFRESH_AUTH_HEADER,TOKEN_TYPE + " " + refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(),loginResponseDto);
    }
}
