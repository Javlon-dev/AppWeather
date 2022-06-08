package com.company.config.filter;

import com.company.config.details.CustomProfileDetails;
import com.company.dto.JwtDTO;
import com.company.exception.AppBadRequestException;
import com.company.service.details.CustomProfileDetailsService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final CustomProfileDetailsService customProfileDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (!Optional.ofNullable(header).isPresent() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            JwtDTO jwtDTO = JwtUtil.decode(token);

            CustomProfileDetails details = customProfileDetailsService.loadUserByUsername(jwtDTO.getEmail());

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(details,
                    null, details.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);

    }


}
