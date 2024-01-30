package com.pjatk.MPR;


import com.pjatk.MPR.exception.CatExceptionHandler;
import com.pjatk.MPR.exception.CatIdTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.accept;

@ExtendWith(MockitoExtension.class)
public class CatControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CatService catService;

    @InjectMocks
    private CatController catController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new CatExceptionHandler(), catController).build();
    }
    
    @Test
    public void GetByIdReturns200WhenCatExistsTest() throws Exception {
        Cat cat = new Cat(5,"Luna");
        when(catService.getCatById(3)).thenReturn(Optional.of(cat));
        mockMvc.perform(MockMvcRequestBuilders.get("/cats/3"))
                .andExpect(jsonPath("$.name").value("Luna"))
                .andExpect(status().isOk());
    }
    public void GetById400IsReturnedIfCatDoesNotExist() throws Exception {
        Cat cat = new Cat(6,"Papaya");

        doThrow(new CatIdTakenException()).when(catService).sendCat(any());
        mockMvc.perform(post("/cat/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 6,\"name\":\"Glupol\"}")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }
}
