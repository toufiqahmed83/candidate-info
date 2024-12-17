package com.deepInsight.candidateinfo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class SupersetGuestTokenService {

    private static final String SECRET_KEY = "VUcwym8CZuecJPqx9tTVrNCwmekGdXfduBSLvVMw2mw";
    private static final long EXPIRATION_TIME_MS = 5 * 60 * 1000; // 5 minutes

    public String generateGuestToken() {
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", "gbf-user");
        userPayload.put("first_name", "gbf-user");
        userPayload.put("last_name", "gbf");

        Map<String, Object> resource = new HashMap<>();
        resource.put("type", "dashboard");
        resource.put("id", "7246192d-81ee-453b-ba48-3b0f59b908f2");
        Map<String, Object> rls = new HashMap<>();
        rls.put("clause", "ORG_ID='64'");

        return Jwts.builder()
                .claim("user", userPayload)
                .claim("resources", new Map[]{resource})
                .claim("type", "db")
                .claim("rls_rules", new Map[]{rls})
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
