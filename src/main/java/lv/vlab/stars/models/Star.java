package lv.vlab.stars.models;

import javax.persistence.*;

@Entity(name = "star")
public class Star {

    private Star() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private Float ra;

    private Float decl;

    @ManyToOne
    @JoinColumn(name = "constellationId")
    private Constellation constellation;

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

    public Float getRa() {
        return ra;
    }

    public void setRa(Float ra) {
        this.ra = ra;
    }

    public Float getDecl() {
        return decl;
    }

    public void setDecl(Float decl) {
        this.decl = decl;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
