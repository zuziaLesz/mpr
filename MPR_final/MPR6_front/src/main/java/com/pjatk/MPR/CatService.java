package com.pjatk.MPR;

import com.pjatk.MPR.exception.CatIdTakenException;
import com.pjatk.MPR.exception.CatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;


@Service
public class CatService {

    RestClient restClient;
    public static final String API_URL = "http://localhost:8080";

    public CatService() {
       restClient = RestClient.create();
    }



//
//    public List<Cat> getCatByName(String name) {
//        List<Cat> cats = catRepository.findAll().stream()
//                .filter(cat -> cat.getName().equals(name))
//                .toList();
//        if (!cats.isEmpty()) return cats;
//        else throw new CatNotFoundException();
//    }
//
//
//
    public List<Cat> getAllCats() {
        List<Cat> cats = restClient
                .get()
                .uri(API_URL +"/cats")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return cats;
    }
    public Optional<Cat> getCatById(int id) {
        Optional<Cat> cat = restClient
                .get()
                .uri(API_URL + "/cats/" + id)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        if (cat.isPresent()) return cat;
        else throw new CatNotFoundException();
    }
    public void sendCat(Cat cat) {
        ResponseEntity<Void> response = restClient.post()
                .uri(API_URL + "/cats")
                .contentType(MediaType.APPLICATION_JSON)
                .body(cat)
                .retrieve()
                .toBodilessEntity();
    }
    public void deleteCatById(int id) {
        ResponseEntity<Void> response = restClient
                .delete()
                .uri(API_URL + "/cats/" + id)
                .retrieve()
                .toBodilessEntity();
    }
    public void editCatName(Cat newCat, int id) {
        ResponseEntity<Void> response = restClient
                .put()
                .uri(API_URL + "/cats/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newCat)
                .retrieve()
                .toBodilessEntity();
    }

}
