package PROSEFA.app.features.auth.infrastructure.security;

import PROSEFA.app.features.user.domain.entity.User;
import PROSEFA.app.features.user.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.*;
import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtProvider jwtProvider;
    private final UserService service;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserService service) {
        this.jwtProvider = jwtProvider;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);
        logger.info("Token resolvido: {}", token);

        if (token == null) {
            throw new SecurityException("Token ausente");
        }

        if (!jwtProvider.validateToken(token)) {
            throw new SecurityException("Token inválido ou expirado");
        }

        String username = jwtProvider.getUsernameFromToken(token);
        logger.info("Usuário extraído do token: {}", username);
        User user = service.findByEmail(username).orElseThrow(
                () -> new SecurityException("Usuário não encontrado")
        );

        logger.info("Usuário encontrado: {}", user.getEmail());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(
                        user.getRole() == null ? "ROLE_USER" : user.getRole()))
        );

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1.0/auth/login")
                || path.startsWith("/api/v1.0/auth/register")
                || path.startsWith("/api/v1.0/swagger-ui")
                || path.startsWith("/api/v1.0/v3/api-docs")
                || path.startsWith("/api/v1.0/h2-console");
    }
}
