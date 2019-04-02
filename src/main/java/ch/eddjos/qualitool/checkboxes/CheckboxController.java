package ch.eddjos.qualitool.checkboxes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/cb")
@CrossOrigin
public class CheckboxController {
    @Autowired
    CheckboxService checkboxService;


    @GetMapping("/")
    public Object getStructure(){
        return checkboxService.getStructure();
    }

    @GetMapping("/{person_id}")
    public Object getPersonalData(@PathVariable("person_id") int id){
        return checkboxService.getPersonalData(id);
    }

    @PutMapping("/{person_id}/{checkbox_id}")
    public Object put(@PathVariable("person_id") int person_id, @PathVariable("checkbox_id") int checkbox_id,@RequestBody PersonalCheckboxDTO dto){
        if(checkboxService.putCheckbox(person_id,checkbox_id,dto)){
            return null;
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
