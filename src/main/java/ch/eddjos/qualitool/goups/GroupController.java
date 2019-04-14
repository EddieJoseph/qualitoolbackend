package ch.eddjos.qualitool.goups;

import ch.eddjos.qualitool.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(value="group")
public class GroupController {
    @Autowired
    GroupService service;
    @Autowired
    AuthService autService;
    @GetMapping
    public ResponseEntity<List<Group>> getAll(@PathVariable(value = "token",required = false) String token ){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(service.findAll(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Group> create(@RequestBody GroupDTO dto, @PathVariable(value = "token",required = false) String token ){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(service.insert(dto),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id")int id, @PathVariable(value = "token",required = false) String token ){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
