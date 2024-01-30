package com.pjatk.MPR;

import com.pjatk.MPR.exception.CatIdTakenException;
import com.pjatk.MPR.exception.CatNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatServiceTest {
    @Mock
    private CatRepository catRepository;
    private AutoCloseable openMocks;
    private CatService catService;

    @BeforeEach
    public void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        catService = new CatService(catRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void findCatThatExistsTest() {
        Optional<Cat> cat = Optional.of(new Cat(1, "Beata"));
        when(catRepository.getCatById(1)).thenReturn(cat);
        Optional<Cat> result = catService.getCatById(1);
        assertEquals(cat, result);
    }

    @Test
    public void findCatThatDoesNotExistTest() {
        when(catRepository.getCatById(4)).thenReturn(Optional.empty());
        assertThrows(CatNotFoundException.class, () -> {
            catService.getCatById(4);
        });
    }

    @Test
    public void sendCatThatDoesNotExistsTest() {
        Cat cat = new Cat(3, "Herbert");
        when(catRepository.getCatById(3)).thenReturn(Optional.empty());
        catService.sendCat(cat);
        verify(catRepository).save(cat);
    }
    @Test
    public void sendCatThatAlreadyExists() {
        Cat cat = new Cat(3, "Herbert");
        when(catRepository.findById(3)).thenReturn(Optional.of(cat));
        assertThrows(CatIdTakenException.class, () -> {
            catService.sendCat(cat);
        });
    }
    @Test
    public void findCatWithGivenNameTest() {
        Cat cat1 = new Cat(1, "Beata Szydlo");
        Cat cat2 = new Cat(2, "Beata");
        Cat cat3 = new Cat(3, "Ferdynant");
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);
        List<Cat> beataCats = new ArrayList<>();
        beataCats.add(cat2);
        when(catRepository.findAll()).thenReturn(cats);
        assertEquals(catService.getCatByName("Beata"), beataCats);
    }
}
