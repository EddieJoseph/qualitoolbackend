package ch.eddjos.qualitool.checkboxes;

import ch.eddjos.qualitool.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController()
@RequestMapping("/cb")
@CrossOrigin
public class CheckboxController {
    @Autowired
    CheckboxService checkboxService;
    @Autowired
    AuthService autService;

    @GetMapping("/")
    public ResponseEntity<List<Checkbox>> getStructure(@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(checkboxService.getStructure(),HttpStatus.OK);
    }

    @GetMapping("/{person_id}")
    public ResponseEntity<List<CheckboxPersonDTO>> getPersonalData(@PathVariable("person_id") int id,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(checkboxService.getPersonalData(id),HttpStatus.OK);
    }

    @PutMapping("/{person_id}/{checkbox_id}")
    public ResponseEntity put(@PathVariable("person_id") int person_id, @PathVariable("checkbox_id") int checkbox_id,@RequestBody PersonalCheckboxDTO dto,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if(checkboxService.putCheckbox(person_id,checkbox_id,dto)){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
