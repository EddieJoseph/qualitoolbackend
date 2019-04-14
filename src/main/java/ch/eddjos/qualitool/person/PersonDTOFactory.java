package ch.eddjos.qualitool.person;

import ch.eddjos.qualitool.checkboxes.Checkbox;
import ch.eddjos.qualitool.checkboxes.CheckboxPersonDTO;
import ch.eddjos.qualitool.checkboxes.CheckboxService;
import ch.eddjos.qualitool.comments.CommentService;
import ch.eddjos.qualitool.goups.GroupDTO;
import ch.eddjos.qualitool.goups.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonDTOFactory {
    @Autowired
    GroupService service;
    @Autowired
    CommentService commentService;
    @Autowired
    CheckboxService checkboxService;



    public PersonDTO create(Person p){
        PersonDTO dto=new PersonDTO();
        dto.id=p.getId();
        dto.firstname= p.getFirstname();
        dto.lastname=p.getLastname();
        dto.nickname=p.getNickname();
        dto.imageUrl=p.getImageUrl();
        dto.organisation=p.getOrganisation();
        dto.groups=service.findByPerson(p).stream().map(g->new GroupDTO(g)).collect(Collectors.toList());
        List<Checkbox> checkbox=checkboxService.getStructure();
        List<Integer> commentnumbers=new ArrayList();
        for(Checkbox cb:checkboxService.getStructure()){
            commentnumbers.add(commentService.findByPersonAndCheckboxComplete(p.getId(),cb.getId()).size());
        }
        dto.commentnumbers=commentnumbers;

        //List<Comment> comments = commentService.
        List<Boolean> cbval=new ArrayList();
        for(CheckboxPersonDTO cb:checkboxService.getPersonalData(p.getId())){
            cbval.add(cb.passed);
        }
        dto.checkboxValues =cbval;





        return dto;
    }


}
