package com.daviholanda.cursomc.resources;

import com.daviholanda.cursomc.security.JWTUtil;
import com.daviholanda.cursomc.security.UserSS;
import com.daviholanda.cursomc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/refresh_token")
    @PostMapping
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {

        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();

    }

}
