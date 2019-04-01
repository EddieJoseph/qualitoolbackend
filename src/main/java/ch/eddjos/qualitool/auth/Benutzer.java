package ch.eddjos.qualitool.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Benutzer {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String nickname;
    @JsonIgnore
    private String password;

}
