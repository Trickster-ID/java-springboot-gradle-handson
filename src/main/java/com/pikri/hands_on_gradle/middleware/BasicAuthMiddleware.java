package com.pikri.hands_on_gradle.middleware;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;

public class BasicAuthMiddleware implements Filter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";

    // Injecting a property from the application.properties file
    @Value("${authorization.basic_auth.username}")
    private String basicUsername;
    @Value("${authorization.basic_auth.password}")
    private String basicPassword;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the Authorization header from the request
        String authHeader = httpRequest.getHeader(AUTH_HEADER);

        // Check if the Authorization header exists and is in Basic Auth format
        if (authHeader == null || !authHeader.startsWith(BASIC_PREFIX)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized: Missing or invalid Authorization header");
            return;
        }

        // Extract and decode the Base64-encoded username and password
        String base64Credentials = authHeader.substring(BASIC_PREFIX.length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

        // Split the credentials into username and password
        String[] values = credentials.split(":", 2);
        if (values.length != 2 || !isValidUser(values[0], values[1])) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized: Invalid username or password");
            return;
        }

        // Continue with the request if the authentication is valid
        chain.doFilter(request, response);
    }

    // You can replace this with a real validation against a database or service
    private boolean isValidUser(String username, String password) {
        System.out.println("username expect: "+ basicUsername+", actual: "+ username);
        System.out.println("password expect: "+ basicPassword+", actual: "+ password);
        // For demo purposes, we validate a hardcoded user
        return basicUsername.equals(username) && basicPassword.equals(password);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
