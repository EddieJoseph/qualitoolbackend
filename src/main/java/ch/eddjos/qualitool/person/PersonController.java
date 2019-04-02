package ch.eddjos.qualitool.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {
    @Autowired
    PersonService personService;
    @GetMapping("/")
    public Object getAll(){
        return personService.getAll();
    }
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") int id){
        return personService.get(id);
    }
    @PutMapping
    public Object put(@RequestBody Person p){
        return personService.put(p);
    }
}
