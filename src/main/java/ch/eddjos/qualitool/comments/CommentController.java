package ch.eddjos.qualitool.comments;


import ch.eddjos.qualitool.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<CommentDTO> getComments(@PathVariable("pid") int personid, @PathVariable("cid") int checkboxId){
        List<Comment> list=service.findByPersonAndCheckboxComplete(personid,checkboxId);
        Collections.sort(list);
        return dtoFactory.create(list);
    }

    @Transactional
    @PostMapping
    public CommentDTO save(@RequestBody CommentDTO dto,@RequestHeader("token") String token){
        dto.authorId=autService.check(token).getId();
        System.out.println(dto);
        Comment comment=dtoFactory.unwrap(dto);
        return dtoFactory.create(service.insert(comment));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int commentId){
        service.delete(commentId);
    }


}
