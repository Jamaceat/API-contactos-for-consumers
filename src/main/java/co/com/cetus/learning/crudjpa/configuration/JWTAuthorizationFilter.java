package co.com.cetus.learning.crudjpa.configuration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import co.com.cetus.learning.crudjpa.utils.ConstantsJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(ConstantsJWT.ENCABEZADO);
        if (header == null || !header.startsWith(ConstantsJWT.PREFIJO_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(ConstantsJWT.ENCABEZADO);
        if (token != null) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ConstantsJWT.CLAVE.getBytes())
                    .build()
                    .parseClaimsJws(token.replace(ConstantsJWT.PREFIJO_TOKEN, "")).getBody();

            String user = claims.getSubject();
            @SuppressWarnings("unchecked")
            List<String> authorities = (List<String>) claims.get("authorities");
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null,
                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
        }

        return null;
    }

}
