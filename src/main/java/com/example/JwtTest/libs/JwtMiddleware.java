package com.example.JwtTest.libs;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Controller
public class JwtMiddleware {


    private String key = "MySecretJwtKey1234";


    private String compactJws;
    
    @RequestMapping(value="/jwt", method=RequestMethod.GET)
    @ResponseBody
    public String generateWebToken() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(dt); 
        c.add(Calendar.DATE, 7);
        dt = c.getTime();
        compactJws = Jwts.builder().setSubject("Choid").signWith(SignatureAlgorithm.HS512, key).setExpiration(new Date()).compact();
        
        return compactJws + " , " + key;
    }

    @RequestMapping(value="/verify", method=RequestMethod.POST)
    @ResponseBody
    public Boolean verifyWebToken() {
        
        return Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject().equals("Choid");
    }
}