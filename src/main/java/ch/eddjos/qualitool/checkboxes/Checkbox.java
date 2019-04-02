package ch.eddjos.qualitool.checkboxes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Checkbox {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @JsonIgnore
    private String description;

    private int level;
    private int severity;

    @Column(nullable = true)
    private Integer minimumachieved;
    @ManyToOne
    @JsonIgnore
    private Checkbox parent;
    @OneToMany
    @JoinColumn(name = "parent_id", updatable = false)
    private List<Checkbox> boxes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public Integer getMinimumachieved() {
        return minimumachieved;
    }

    public void setMinimumachieved(Integer minimumachieved) {
        this.minimumachieved = minimumachieved;
    }

    public Checkbox getParent() {
        return parent;
    }

    public void setParent(Checkbox parent) {
        this.parent = parent;
    }

    public List<Checkbox> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Checkbox> boxes) {
        this.boxes = boxes;
    }


}
