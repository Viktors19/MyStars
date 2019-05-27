package lv.vlab.stars.models;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Entity(name = "user")
public class User extends ResourceSupport {

    public User() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    private String name;

    private String apiKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}