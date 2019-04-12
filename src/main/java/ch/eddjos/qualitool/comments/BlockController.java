package ch.eddjos.qualitool.comments;

import ch.eddjos.qualitool.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("block")
@CrossOrigin
public class BlockController {
    @Autowired
    private BlockService service;
    @Autowired
    private CommentDTOFactory dtoFactory;
    @Autowired
    AuthService autService;


    @GetMapping
    public ResponseEntity<List<BlockDTO>> list(@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(dtoFactory.createBL(service.findAll()),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlockDTO> get(@PathVariable("id") int id,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(dtoFactory.create(service.findOne(id)),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BlockDTO> save(@RequestBody BlockDTO block,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(dtoFactory.create(service.insert(dtoFactory.unwrap(block))),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
