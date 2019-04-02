package ch.eddjos.qualitool.checkboxes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalCheckboxRepo extends JpaRepository<PersonalCheckbox, PersonalCheckbox.PersonalCheckboxId> {
}
