package ch.eddjos.qualitool.comments;


import ch.eddjos.qualitool.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService service;
    @Autowired
    private CommentDTOFactory dtoFactory;
    @Autowired
    AuthService autService;

    @GetMapping("/{pid}/{cid}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable("pid") int personid, @PathVariable("cid") int checkboxId,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<Comment> list=service.findByPersonAndCheckboxComplete(personid,checkboxId);
        Collections.sort(list);
        return new ResponseEntity(dtoFactory.create(list),HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CommentDTO> save(@RequestBody CommentDTO dto,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        dto.authorId=autService.check(token).getId();
        System.out.println(dto);
        Comment comment=dtoFactory.unwrap(dto);
        return new ResponseEntity(dtoFactory.create(service.insert(comment)),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int commentId, @RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        service.delete(commentId);
        return new ResponseEntity(HttpStatus.OK);
    }


}
