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
        if(dto.negativ&&dto.positiv){
            return true;
        }
        if(pc!=null){
            if(pc.isSighted()&&dto.sighted){
                return true;
            }
            pc.setNegativ(dto.negativ);
            pc.setPositiv(dto.positiv);
            pc.setSighted(dto.sighted);
            pc.setPassed(dto.passed);
            testCheckboxes(person_id);
            return true;
        }
        return false;
    }

    @Transactional
    public void  testCheckboxes(int personId){
        List<Checkbox> pclist=getStructure();
        for(Checkbox cb:pclist){
            evaluateCheckbox(cb,personId);
        }
    }

    @Transactional
    public void evaluateCheckbox(Checkbox cb, int personId){
        int nrOfSub=0;
        int subFailed=0;
        int criticalFailed=0;
        int criticalNotObservedYet=0;
        int nonCriticalFailed=0;
        int nonCriticalPassed=0;
        PersonalCheckbox  pcb=personalCheckboxRepo.getOne(new PersonalCheckbox.PersonalCheckboxId(cb.getId(),personId));
        for(Checkbox subCb:cb.getBoxes()){
            PersonalCheckbox subPCb=personalCheckboxRepo.getOne(new PersonalCheckbox.PersonalCheckboxId(subCb.getId(),personId));
            if(pcb.isSighted()){
                subPCb.setSighted(true);
            }


            if(subCb.getBoxes().size()>0) {
                evaluateCheckbox(subCb, personId);
            }
            if(subCb.getSeverity()==0){//Critical
                if(subPCb.isNegativ()){
                    criticalFailed++;
                }
                if(!subPCb.isNegativ()&&!subPCb.isPositiv()&&!subPCb.isSighted()){
                    criticalNotObservedYet++;
                }
            }
            if(subCb.getSeverity()==1){//Normal
                nrOfSub++;
                if(subPCb.isNegativ()){
                    nonCriticalFailed++;
                }
                if(subPCb.isPositiv()||(subPCb.isSighted()&&!subPCb.isNegativ())){
                    nonCriticalPassed++;
                }
            }
            if(subCb.getSeverity()==2){//Nicetohave

            }
        }

        boolean minimum=false;
        if(cb.getSeverity()!=2) {
            if (cb.getMinimumachieved() <= nonCriticalPassed) {
                minimum = true;
                pcb.setPositiv(true);
                pcb.setNegativ(false);
            } else if (nrOfSub - nonCriticalFailed < cb.getMinimumachieved()) {
                minimum = false;
                pcb.setPositiv(false);
                pcb.setNegativ(true);
            } else {
                minimum = false;
                pcb.setPositiv(false);
                pcb.setNegativ(false);
            }
        }
        if(criticalFailed>0){
            pcb.setPassed(false);
        }else{
            if(criticalNotObservedYet>0||!minimum){
                pcb.setPassed(null);
            }else{
                pcb.setPassed(true);
            }
        }






    }





}
