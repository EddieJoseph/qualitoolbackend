package ch.eddjos.qualitool.comments;


import ch.eddjos.qualitool.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return dtoFactory.create(service.findByPersonAndCheckbox(personid,checkboxId));
    }

    @PostMapping
    public CommentDTO save(@RequestBody CommentDTO dto,@RequestHeader("token") String token){
        Comment comment=dtoFactory.unwrap(dto,autService.check(token));
        return dtoFactory.create(service.insert(comment));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int commentId){
        service.delete(commentId);
    }


}
