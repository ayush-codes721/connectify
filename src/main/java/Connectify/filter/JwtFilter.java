package Connectify.filter;

import Connectify.Servcies.JWT.JwtService;
import Connectify.Servcies.User.UserService;
import Connectify.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            String requestHeaderToken = request.getHeader("Authorization");

            if (requestHeaderToken==null || !requestHeaderToken.startsWith("Bearer") ) {

                filterChain.doFilter(request, response);
                return;
            }

            String token = requestHeaderToken.split("Bearer")[1].trim();

            Long id = jwtService.getUserIdFormToken(token);
            User user = userService.findUserById(id);

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(user, null, null);

                SecurityContextHolder.getContext().setAuthentication(upa);


            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {

            handlerExceptionResolver.resolveException(request, response, null, e);
        }

    }
}
