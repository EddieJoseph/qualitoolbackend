package ch.eddjos.qualitool.comments;


import ch.eddjos.qualitool.auth.AuthService;
import ch.eddjos.qualitool.person.Person;
import ch.eddjos.qualitool.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Autowired
    private PersonService personService;


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
    @PostMapping("/star/{id}/{value}")
    public ResponseEntity setStared(@PathVariable("id") int id, @PathVariable("value") boolean value,@RequestHeader(value = "token",required = false) String token){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        service.setStared(id,value);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/download/{id}/{token}")
    public ResponseEntity getDownload(@PathVariable("id")int id, @PathVariable(value = "token",required = false) String token ){
        try {
            autService.getAuthentication(token);
        }catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Person p = personService.get(id);
        byte[] data=service.generateCommentFile(id);
        ByteArrayResource resource = new ByteArrayResource(data);
        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"atachment;filename=\"Beobachtungen_"+p.getNickname()+".xlsx\"");
        headers.add("filename=","test.xlsx");
        headers.add(HttpHeaders.CONTENT_LENGTH,Integer.toString(data.length));

        //return new ResponseEntity(resource,HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType("application/xlsx"))
                //.contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }





}
