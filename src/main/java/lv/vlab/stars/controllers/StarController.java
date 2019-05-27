package lv.vlab.stars.controllers;

import lv.vlab.stars.exceptions.ConstellationNotFoundException;
import lv.vlab.stars.exceptions.UnauthorizedException;
import lv.vlab.stars.models.Constellation;
import lv.vlab.stars.models.Star;
import lv.vlab.stars.models.User;
import lv.vlab.stars.repositories.ConstellationRepository;
import lv.vlab.stars.repositories.StarRepository;
import lv.vlab.stars.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/stars")
public class StarController {

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConstellationRepository constellationRepository;

    @GetMapping("")
    public List<Star> all() {
        return (List<Star>) starRepository.findAll();
    }

    @PostMapping("")
    public Star newStar(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @RequestBody Star star) {
        return updateStar(apiKey, star, Integer.toUnsignedLong(0));
    }

    @GetMapping("/{starId}")
    public Star getStar(@PathVariable long starId) {
        return starRepository.findById(starId).get();
    }

    @PatchMapping("/{starId}")
    public Star editStar(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @PathVariable long starId, @RequestBody Star star) {
        return updateStar(apiKey, star, starId);
    }

    @DeleteMapping("/{starId}")
    public void deleteStar(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @PathVariable long starId) {
        Optional<User> userSearch = userRepository.findByApiKey(apiKey);
        if (userSearch.isPresent()) {
            starRepository.deleteById(starId);
        } else {
            throw new UnauthorizedException();
        }
    }

    private Star updateStar(String apiKey, Star star, Long starId) {
        Optional<User> userSearch = userRepository.findByApiKey(apiKey);
        if (userSearch.isPresent()) {
            User user = userSearch.get();
            star.setUser(user);
            if (starId != 0) {
                star.setId(starId);
            }
            boolean isConstellationFound = false;
            if (star.getConstellation().getConstellationId() != null) {
                Optional<Constellation> constellationSearch = constellationRepository.findById(star.getConstellation().getConstellationId());
                if (constellationSearch.isPresent()) {
                    System.out.println(constellationSearch.get());
                    star.setConstellation(constellationSearch.get());
                    isConstellationFound = true;
                }
            }
            if (star.getConstellation().getName() != null && !isConstellationFound) {
                Optional<Constellation> constellationSearch = constellationRepository.findByName(star.getConstellation().getName());
                if (constellationSearch.isPresent() && constellationSearch.get().getConstellationId() != null) {
                    System.out.println(constellationSearch.get());
                    star.setConstellation(constellationSearch.get());
                } else {
                    throw new ConstellationNotFoundException();
                }
            } else {
                throw new ConstellationNotFoundException();
            }
        } else {
            throw new UnauthorizedException();
        }
        return starRepository.save(star);
    }

}
