package ch.eddjos.qualitool.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    BenutzerDTOFactory factory;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value="/login",produces = "application/json")
    public Object login(@RequestHeader("username") String username,@RequestHeader("password") String password){
        Benutzer user=autService.login(username,password);
        if(user==null){
            logger.info("Login atempt from {}, {}",username, password);
            return new ResponseEntity<>("Username or password incorrect.",HttpStatus.UNAUTHORIZED);
        }
        logger.info("Sucessful login for {}",username);
        return factory.ctrate(user);
    }

    @PostMapping(value="/logout",produces = "application/json")
    public Object logout(@RequestHeader("token") String token){
        return autService.logout(token);
    }

    @PostMapping(value="/check",produces = "application/json")
    public Object check(@RequestHeader("token") String token){
        return factory.ctrate(autService.check(token));
    }


}
