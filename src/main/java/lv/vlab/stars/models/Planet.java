package lv.vlab.stars.models;

import javax.persistence.*;

@Entity(name = "planet")
public class Planet {

    private Planet() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "starId")
    private Star Star;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Star getStar() {
        return Star;
    }

    public void setStar(Star star) {
        Star = star;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
