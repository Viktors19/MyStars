package lv.vlab.stars.models;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Entity(name = "constellation")
public class Constellation extends ResourceSupport {

    private Constellation() {
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long constellationId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Long getConstellationId() {
        return constellationId;
    }

    public void setConstellationId(Long constellationId) {
        this.constellationId = constellationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
