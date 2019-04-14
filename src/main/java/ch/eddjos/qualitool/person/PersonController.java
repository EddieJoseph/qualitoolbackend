package ch.eddjos.qualitool.person;

import ch.eddjos.qualitool.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {
    @Autowired
    PersonService personService;
    @Autowired
    AuthService autService;
    @Autowired
    PersonDTOFactory factory;

    @GetMapping("/")
    public ResponseEntity<List<Person>> getAll(@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return  new ResponseEntity(personService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable("id") int id,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return  new ResponseEntity(factory.create(personService.get(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Person> put(@RequestBody Person p,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(factory.create(personService.put(p)), HttpStatus.OK);
    }
}
