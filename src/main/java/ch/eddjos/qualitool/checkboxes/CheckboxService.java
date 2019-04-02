package ch.eddjos.qualitool.checkboxes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckboxService {
    @Autowired
    private CheckboxRepository repo;
    @Autowired
    private PersonalCheckboxRepo personalCheckboxRepo;
    @Autowired
    private CheckboxPersonDTOFactory factory;

    public List<Checkbox> getStructure(){
        //return repo.findAll();
        return repo.findAllByLevelEqualsOrderById(0);
    }

    public List<CheckboxPersonDTO> getPersonalData(int person_id){
        return getStructure().stream().map(cb->factory.createCheckboxPersonDTOFrom(cb,person_id)).collect(Collectors.toList());
    }
    @Transactional
    public boolean putCheckbox(int person_id,int checkbox_id,PersonalCheckboxDTO dto){
        PersonalCheckbox pc=personalCheckboxRepo.getOne(new PersonalCheckbox.PersonalCheckboxId(checkbox_id,person_id));
        if(pc!=null){
            pc.setNegativ(dto.negativ);
            pc.setPositiv(dto.positiv);
            pc.setSighted(dto.sighted);
            pc.setPassed(dto.passed);
            return true;
        }
        return false;
    }




}
