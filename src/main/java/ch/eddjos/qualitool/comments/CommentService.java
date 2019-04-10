package ch.eddjos.qualitool.comments;

import ch.eddjos.qualitool.checkboxes.Checkbox;
import ch.eddjos.qualitool.checkboxes.CheckboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository repo;
    @Autowired
    CheckboxRepository checkboxRepo;

    public List<Comment> findByPerson(int personId){
        return repo.findAllByPerson_Id(personId);
    }

    public List<Comment> findByAuthor(int userId){
        return repo.findAllByUser_Id(userId);
    }

    public List<Comment> findByPersonAndCheckbox(int personId, int checkboxId){
        return repo.findAllByPerson_IdAndCheckbox_IdOrderById(personId,checkboxId);
    }

    public List<Comment> findByPersonAndCheckboxComplete(int personId, int checkboxId){
        List<Comment> list = findByPersonAndCheckbox(personId, checkboxId);
        Checkbox cb = checkboxRepo.getOne(checkboxId);
        //if(cb.getBoxes().size()>0){
         for(Checkbox c:cb.getBoxes()){
             list.addAll(findByPersonAndCheckboxComplete(personId,c.getId()));
         }
        //}
        return list;
    }

    public Comment insert(Comment comment){
        //System.out.println(comment);
        return repo.save(comment);
    }

    public void delete(int id){
        repo.deleteById(id);
    }


}
