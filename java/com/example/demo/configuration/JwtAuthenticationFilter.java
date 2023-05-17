package com.example.demo.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component                    //allows spring to automatically detect custom beans
@RequiredArgsConstructor
//generates a constructor with 1 parameter for each field that requires special handling(using that private final field)
public class JwtAuthenticationFilter extends OncePerRequestFilter {   //it s a filter that is guaranteed to be executed only once for a given request

    private final JwtService jwtService;   //a class to manipulate the token
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String header = request.getHeader(HEADER_STRING);
        final String jwt;                                                 //token
        final String user;                                                //user details
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);                      //we go to the next filter
            return;                                                       //we don't want to continue after this
        }

        jwt = header.substring(7);                              //extracting the token; 7 from "Bearer "
        user = jwtService.extractUsername(jwt);                           //extract the username
        //if the username exists and it is not authenticated
        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails details = this.userDetailsService.loadUserByUsername(user);
            if (jwtService.isValidToken(jwt, details)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
