package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @Mock
    Map<String, Object> stringObjectMap;

    @InjectMocks
    VetController vetController;

    List<Vet> vetsList = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        vetsList.add(new Vet());
        given(clinicService.findVets()).willReturn(vetsList);
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    void testControllerShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
          .andExpect(status().isOk())
          .andExpect(model().attributeExists("vets"))
          .andExpect(view().name("vets/vetList"));
    }

    @Test
    void showVetList() {
        //when
        String viewName = vetController.showVetList(stringObjectMap);

        //then
        then(clinicService).should().findVets();
        then(stringObjectMap).should().put(anyString(), any());
        assertThat("vets/vetList").isEqualToIgnoringCase(viewName);
    }

    @Test
    void showResourcesVetList() {
        //when
        Vets vets = vetController.showResourcesVetList();

        //then
        then(clinicService).should().findVets();
        assertThat(vets.getVetList()).hasSize(1);
    }
}