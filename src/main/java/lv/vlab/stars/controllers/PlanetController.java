package lv.vlab.stars.controllers;

import lv.vlab.stars.exceptions.ConstellationNotFoundException;
import lv.vlab.stars.exceptions.StarNotFoundException;
import lv.vlab.stars.exceptions.UnauthorizedException;
//import lv.vlab.stars.models.Constellation;
//import lv.vlab.stars.models.Star;
import lv.vlab.stars.models.Planet;
import lv.vlab.stars.models.Star;
import lv.vlab.stars.models.User;
import lv.vlab.stars.repositories.ConstellationRepository;
import lv.vlab.stars.repositories.PlanetRepository;
//import lv.vlab.stars.repositories.StarRepository;
import lv.vlab.stars.repositories.StarRepository;
import lv.vlab.stars.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/planets")
public class PlanetController {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StarRepository starRepository;

    @GetMapping("")
    public List<Planet> all() {
        return (List<Planet>) planetRepository.findAll();
    }

    @PostMapping("")
    public Planet newPlanet(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @RequestBody Planet planet) {
        return updatePlanet(apiKey, planet, Integer.toUnsignedLong(0));
    }

    @GetMapping("/{planetId}")
    public Planet getPlanet(@PathVariable long planetId) {
        return planetRepository.findById(planetId).get();
    }

    @PatchMapping("/{planetId}")
    public Planet editPlanet(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @PathVariable long planetId, @RequestBody Planet planet) {
        return updatePlanet(apiKey, planet, planetId);
    }

    @DeleteMapping("/{planetId}")
    public void deletePlanet(@RequestHeader(value = "Authorization", defaultValue = "") String apiKey, @PathVariable long planetId) {
        Optional<User> userSearch = userRepository.findByApiKey(apiKey);
        if (userSearch.isPresent()) {
            planetRepository.deleteById(planetId);
        } else {
            throw new UnauthorizedException();
        }
    }

    private Planet updatePlanet(String apiKey, Planet planet, Long planetId) {
            Optional<User> userSearch = userRepository.findByApiKey(apiKey);
        if (userSearch.isPresent()) {
            User user = userSearch.get();
            planet.setUser(user);
            if (planetId != 0) {
                planet.setId(planetId);
            }
            boolean isStarFound = false;
            if (planet.getStar().getId() != null) {
                Optional<Star> starSearch = starRepository.findById(planet.getStar().getId());
                if (starSearch.isPresent()) {
                    System.out.println(starSearch.get());
                    planet.setStar(starSearch.get());
                    isStarFound = true;
                }
            }
            if (planet.getStar().getName() != null && !isStarFound) {
                Optional<Star> starSearch = starRepository.findByName(planet.getStar().getName());
                if (starSearch.isPresent() && starSearch.get().getId() != null) {
                    System.out.println(starSearch.get());
                    planet.setStar(starSearch.get());
                } else {
                    throw new StarNotFoundException();
                }
            } else {
                throw new StarNotFoundException();
            }
        } else {
            throw new UnauthorizedException();
        }
        return planetRepository.save(planet);
    }

}
