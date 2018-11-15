package br.com.gabrielspassos.middleware.controllers;

import br.com.gabrielspassos.middleware.controllers.dto.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class LoginController implements BaseVersion {

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        return ok(login);
    }
}
