package ch.eddjos.qualitool.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("block")
@CrossOrigin
public class BlockController {
    @Autowired
    private BlockService service;
    @Autowired
    private CommentDTOFactory dtoFactory;
    @GetMapping
    public List<BlockDTO> list(){
        return dtoFactory.createBL(service.findAll());
    }
    @GetMapping("/{id}")
    public BlockDTO get(@PathVariable("id") int id){
        return dtoFactory.create(service.findOne(id));
    }
    @PostMapping
    public BlockDTO save(@RequestBody BlockDTO block){
        return dtoFactory.create(service.insert(dtoFactory.unwrap(block)));
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        service.delete(id);
    }
}
