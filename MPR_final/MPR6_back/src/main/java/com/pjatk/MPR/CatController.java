package com.pjatk.MPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CatController {
    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello mate";
    }

    @GetMapping("/cats/{id}")
    public Optional<Cat> getCatById(@PathVariable int id) {
        return catService.getCatById(id);
    }
    @PostMapping("/cats")
    public Cat sendCat(@RequestBody Cat cat) {
        catService.sendCat(cat);
        return cat;
    }
    @DeleteMapping("/cats/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable int id) {
        catService.deleteCatById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("cats/byName/{name}")
    public List<Cat> getCatByName(@PathVariable String name) { return catService.getCatByName(name);}
    @GetMapping("/cats")
    public List<Cat> getAllCats() { return catService.getAllCats();}
    @PutMapping("cats/{id}")
    public ResponseEntity<Void> editCat(@RequestBody Cat cat, @PathVariable int id) {
        catService.editCatName(cat, id);
        return ResponseEntity.ok().build();
    }
}

