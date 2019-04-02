package ch.eddjos.qualitool.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    AuthService autService;

    @PostMapping(value="/login",produces = "application/json")
    public Object login(@RequestHeader("username") String username,@RequestHeader("password") String password){
        Benutzer user=autService.login(username,password);
        if(user==null){
            return new ResponseEntity<>("Username or password incorrect.",HttpStatus.UNAUTHORIZED);
        }
        return user;
    }

    @PostMapping(value="/logout",produces = "application/json")
    public Object logout(@RequestHeader("token") String token){
        return autService.logout(token);
    }

    @PostMapping(value="/check",produces = "application/json")
    public Object check(@RequestHeader("token") String token){
        return autService.check(token);
    }


}
