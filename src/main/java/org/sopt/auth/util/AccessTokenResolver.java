package org.sopt.auth.util;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.auth.jwt.exception.EmptyTokenException;
import org.sopt.auth.jwt.exception.MalformedTokenException;

public class AccessTokenResolver {

    public static String resolve(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new EmptyTokenException();
        }

        if (!header.startsWith("Bearer ")) {
            throw new MalformedTokenException();
        }
        return header.substring(7);
    }
}