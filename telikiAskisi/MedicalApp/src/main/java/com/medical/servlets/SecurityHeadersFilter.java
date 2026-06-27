package com.medical.servlets;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

// Εφαρμογή του φίλτρου σε ΟΛΕΣ τις σελίδες (/*)
@WebFilter("/*")
public class SecurityHeadersFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 1. Προστασία από Clickjacking
        httpResponse.setHeader("X-Frame-Options", "DENY");
        
        // 2. Προστασία από MIME Sniffing
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        
        // 3. XSS Protection
        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
        
        // 4. HSTS: Εξαναγκασμός HTTPS για ένα χρόνο
        httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

        // 5. Content Security Policy (CSP): Αυστηροί κανόνες για scripts/styles
        httpResponse.setHeader("Content-Security-Policy", 
            "default-src 'self'; " + 
            "script-src 'self' https://cdn.jsdelivr.net; " + 
            "style-src 'self' https://cdn.jsdelivr.net; " + 
            "img-src 'self' data:; " + 
            "font-src 'self' https://cdn.jsdelivr.net; " +
            "frame-ancestors 'none'; " + 
            "form-action 'self';");

        // 6. Cache Control: Να μην αποθηκεύονται ευαίσθητα δεδομένα
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");

        // Συνέχεια της αίτησης
        chain.doFilter(request, response);
    }
}