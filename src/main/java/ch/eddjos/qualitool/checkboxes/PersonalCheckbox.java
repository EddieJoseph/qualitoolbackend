package ch.eddjos.qualitool.checkboxes;

import ch.eddjos.qualitool.person.Person;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
public class PersonalCheckbox {
    @Id
    PersonalCheckboxId id;
    private boolean positiv;
    private boolean negativ;
    private boolean sighted;
    private Boolean passed;



    public PersonalCheckbox(){
        id=new PersonalCheckboxId();
    };

    public PersonalCheckbox(Checkbox c, Person p){
        id=new PersonalCheckboxId(c,p);
    }


    public PersonalCheckboxId getId() {
        return id;
    }

    public void setId(PersonalCheckboxId id) {
        this.id = id;
    }

    public boolean isPositiv() {
        return positiv;
    }

    public void setPositiv(boolean positiv) {
        this.positiv = positiv;
    }

    public boolean isNegativ() {
        return negativ;
    }

    public void setNegativ(boolean negativ) {
        this.negativ = negativ;
    }

    public boolean isSighted() {
        return sighted;
    }

    public void setSighted(boolean sighted) {
        this.sighted = sighted;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }
    @Embeddable
    public static class PersonalCheckboxId implements Serializable {
        int checkbox_id;
        int person_id;

        public PersonalCheckboxId(){}

        public PersonalCheckboxId(int checkbox_id, int person_id){
            this.checkbox_id=checkbox_id;
            this.person_id=person_id;
        }
        public PersonalCheckboxId(Checkbox c, Person p){
            this(c.getId(),p.getId());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonalCheckboxId that = (PersonalCheckboxId) o;
            return checkbox_id == that.checkbox_id &&
                    person_id == that.person_id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(checkbox_id, person_id);
        }

        public int getCheckbox_id() {
            return checkbox_id;
        }

        public void setCheckbox_id(int checkbox_id) {
            this.checkbox_id = checkbox_id;
        }

        public int getPerson_id() {
            return person_id;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }
    }
}
