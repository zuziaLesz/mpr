package com.pjatk.MPR;

import com.pjatk.MPR.exception.CatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.Optional;

@Controller
public class WebController {
    private final CatService catService;
    @GetMapping(value = "/welcome")
    public String getWelcomeView() {return "welcome";}

    @Autowired
    public WebController(CatService catService) {this.catService = catService;}

    @GetMapping(value = "/allCats")
    public String getCats(Model model) {
        model.addAttribute("cats", catService.getAllCats());
        return "allCats";
    }
    @GetMapping(value = "/addCat")
    public String addCat(Model model) {
        model.addAttribute("cat", new Cat(0,""));
        return "addCat";
    }
    @PostMapping(value = "/addCat")
    public String addCat(Cat cat, Model model) {
        catService.sendCat(cat);
        return "redirect:/allCats";
    }
    @GetMapping(value = "/editCat/{id}")
    public String editCat(Model model, @PathVariable int id) {
        Optional<Cat> catToEdit = catService.getCatById(id);
        if(catToEdit.isPresent()) {
            model.addAttribute("cat", catToEdit.get());
            return "editCat";
        }
        else throw new CatNotFoundException();
    }
    @PostMapping(value = "/editCat/{id}")
    public String editCat (Model model, Cat newCat, @PathVariable int id) {
        catService.editCatName(newCat, id);
        return "redirect:/allCats";
    }
    @GetMapping(value = "/deleteCat/{id}")
    public String deleteCat(@PathVariable int id, Model model) {
        Optional<Cat> catToDelete = catService.getCatById(id);
        if(catToDelete.isPresent()) {
            model.addAttribute("cat", catToDelete.get());
            return "deleteCat";
        }
        else throw new CatNotFoundException();
    }
    @PostMapping(value = "/deleteCat/{id}")
    public String deleteCat(Model model, @PathVariable int id) {
        catService.deleteCatById(id);
        return "redirect:/allCats";
    }

}
