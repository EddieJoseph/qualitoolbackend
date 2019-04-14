package ch.eddjos.qualitool.auth;

import ch.eddjos.qualitool.goups.GroupDTO;

import java.util.List;

public class BenutzerDTO {
    public int id;
    public String firstName;
    public String lastName;
    public String nickname;
    public String ip;
    public String token;
    public List<GroupDTO> groups;
}
