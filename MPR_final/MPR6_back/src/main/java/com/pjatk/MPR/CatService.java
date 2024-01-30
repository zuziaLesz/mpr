package com.pjatk.MPR;

import com.pjatk.MPR.exception.CatIdTakenException;
import com.pjatk.MPR.exception.CatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CatService {
    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
        this.catRepository.save(new Cat(1, "Beata"));
        this.catRepository.save(new Cat(2, "Daisy"));
        this.catRepository.save(new Cat(3, "Michal"));
        this.catRepository.save(new Cat(4,"Jaroslaw"));
    }

    public Optional<Cat> getCatById(int id) {
        Optional<Cat> cat = catRepository.getCatById(id);
        if (cat.isPresent()) return cat;
        else throw new CatNotFoundException();

    }

    public Cat sendCat(Cat cat) {
        if (catRepository.findById(cat.getId()).isEmpty()) return catRepository.save(cat);
        else throw new CatIdTakenException();
    }

    public void deleteCatById(int id) {
        if (catRepository.findById(id).isPresent()) catRepository.deleteById(id);
        else throw new CatNotFoundException();
    }

    public List<Cat> getCatByName(String name) {
        List<Cat> cats = catRepository.findAll().stream()
                .filter(cat -> cat.getName().equals(name))
                .toList();
        if (!cats.isEmpty()) return cats;
        else throw new CatNotFoundException();
    }

    public List<Cat> getAllCats() {
        if (!catRepository.findAll().isEmpty()) return catRepository.findAll();
        else throw new CatNotFoundException();
    }

    public void editCatName(Cat newCat, int id) {
        Optional<Cat> oldCat = catRepository.getCatById(id);
        if(oldCat.isPresent()) {
            oldCat.get().setName(newCat.getName());
            catRepository.save(oldCat.get());
        }
        else throw new CatNotFoundException();
    }
}
