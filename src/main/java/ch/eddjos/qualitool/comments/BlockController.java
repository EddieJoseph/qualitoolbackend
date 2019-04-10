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
    @GetMapping
    public List<Block> list(){
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Block get(@PathVariable("id") int id){
        return service.findOne(id);
    }
    @PostMapping
    public Block save(@RequestBody Block block){
        return service.insert(block);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        service.delete(id);
    }
}
