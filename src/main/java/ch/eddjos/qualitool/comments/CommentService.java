package ch.eddjos.qualitool.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository repo;

    public List<Comment> findByPerson(int personId){
        return repo.findAllByPerson_Id(personId);
    }

    public List<Comment> findByAuthor(int userId){
        return repo.findAllByUser_Id(userId);
    }

    public List<Comment> findByPersonAndCheckbox(int personId, int checkboxId){
        return repo.findAllByPerson_IdAndCheckbox_IdOrderById(personId,checkboxId);
    }

    public Comment insert(Comment comment){
        return repo.save(comment);
    }

    public void delete(int id){
        repo.deleteById(id);
    }


}
