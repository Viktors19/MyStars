package lv.vlab.stars.controllers;

import lv.vlab.stars.exceptions.UnauthorizedException;
import lv.vlab.stars.models.Constellation;
import lv.vlab.stars.models.User;
import lv.vlab.stars.repositories.ConstellationRepository;
import lv.vlab.stars.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/constellations")
public class ConstellationController {

    @Autowired
    private ConstellationRepository constellationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<Constellation> all() {
        return (List<Constellation>) constellationRepository.findAll();
    }

    @PostMapping("")
    public Constellation newConstellation(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @RequestBody Constellation constellation) {
        return updateConstellation(apiKey, constellation, Integer.toUnsignedLong(0));
    }

    @GetMapping("/{constellationId}")
    public Constellation getConstellation(@PathVariable long constellationId) {
        Constellation constellation = constellationRepository.findById(constellationId).get();
        constellation.add(linkTo(methodOn(UserController.class).getUser(constellation.getUser().getUserId())).withRel("author"));
        return constellation;
    }

    @PatchMapping("/{constellationId}")
    public Constellation editConstellation(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @PathVariable long constellationId, @RequestBody Constellation constellation) {
        return updateConstellation(apiKey, constellation, constellationId);
    }

    @DeleteMapping("/{constellationId}")
    public void deleteConstellation(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @PathVariable long constellationId) {
        Optional<User> userSearch = userRepository.findByApiKey(apiKey);
        if (userSearch.isPresent()) {
            constellationRepository.deleteById(constellationId);
        } else {
            throw new UnauthorizedException();
        }
    }

    private Constellation updateConstellation (String apiKey, Constellation constellation, Long constellationId) {
        Optional<User> userSearch = userRepository.findByApiKey(apiKey);
        if (userSearch.isPresent()) {
            User user = userSearch.get();
            constellation.setUser(user);
            if (constellationId != 0) {
                constellation.setConstellationId(constellationId);
            }
            return constellationRepository.save(constellation);
        } else {
            throw new UnauthorizedException();
        }
    }
}
