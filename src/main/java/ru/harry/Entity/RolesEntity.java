package ru.harry.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "webapiam"/*, catalog = ""*/)
@Getter
@Setter
public class RolesEntity {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "Name", nullable = false, length = 50)
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;
//    public RolesEntity() {
//    }

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

    @Override
    public String toString()
    {    return id+ "\t| " + name + "|";    }

}
