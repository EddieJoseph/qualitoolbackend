package ch.eddjos.qualitool.auth;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {
    @Autowired
    BenutzerRepository repo;

    @Transactional
    public Benutzer login(String username, String password){
        Benutzer user=repo.findBenutzerByNicknameEquals(username);
        if(user==null){
            return null;
        }
        if(!user.getPassword().equals(password)){
            return null;
        }
        user.setToken(generateToken());
        return user;
    }

    @Transactional
    public boolean logout(String token){
        Benutzer user = repo.findBenutzerByTokenEquals(token);
        if(user!=null){
            user.setToken("");
            return true;
        }
        return false;
    }

    public boolean authenticated(String token){
        if(token==null||token.length()<10){
            return false;
        }
        Benutzer user = repo.findBenutzerByTokenEquals(token);
        if(user!=null){
            return true;
        }else{
            return false;
        }
    }

    public Benutzer check(String token){
        if(token==null||token.length()<10){
            return null;
        }
        Benutzer user = repo.findBenutzerByTokenEquals(token);
        if(user!=null){
            return user;
        }else{
            return null;
        }
    }

    private String generateToken(){
        return RandomString.make(24);
    }

}
