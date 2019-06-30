package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.repository.ComponentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComponentServiceTest {

    @InjectMocks
    private ComponentService componentService;
    @Mock
    private ComponentRepository componentRepository;

    @Test
    public void findAllTest() {
        //GIVEN
        List<Component> expectedListComponents = new ArrayList<>();
        expectedListComponents.add(new Component());
        expectedListComponents.add(new Component());
        expectedListComponents.add(new Component());
        Page<Component> expectedComponents = new PageImpl<>(expectedListComponents);
        Pageable pageable = PageRequest.of(0, 10);
        when(componentRepository.findAll(pageable)).thenReturn(expectedComponents);

        //WHEN
        Page<Component> components = componentService.findAll(pageable);

        //THEN
        assertEquals(expectedComponents, components);
    }

    @Test
    public void createFromDTOTest() {
        //GIVEN
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Component");
        componentDTO.setMeasure(Measure.GRAM);
        Component expectedComponent = new Component();
        expectedComponent.setName(componentDTO.getName());
        expectedComponent.setMeasure(componentDTO.getMeasure());
        when(componentRepository.save(any(Component.class))).thenReturn(expectedComponent);

        //WHEN
        Component component = componentService.createFromDTO(componentDTO);

        //THEN
        assertEquals(expectedComponent, component);
    }

    @Test
    public void updateExistentComponentFromDTOTest() {
        //GIVEN
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Component");
        componentDTO.setMeasure(Measure.GRAM);
        Long componentId = 1L;
        Component expectedComponent = new Component();
        expectedComponent.setId(componentId);
        expectedComponent.setName(componentDTO.getName());
        expectedComponent.setMeasure(componentDTO.getMeasure());
        when(componentRepository.findById(componentId)).thenReturn(Optional.of(expectedComponent));
        when(componentRepository.save(any(Component.class))).thenReturn(expectedComponent);

        //WHEN
        Component component = componentService.updateFromDTO(componentId, componentDTO);

        //THEN
        assertEquals(expectedComponent, component);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateNonexistentComponentFromDTOTest() {
        //GIVEN
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Component");
        componentDTO.setMeasure(Measure.GRAM);
        Long componentId = 1L;
        when(componentRepository.findById(componentId)).thenReturn(Optional.empty());

        //WHEN
        componentService.updateFromDTO(componentId, componentDTO);
    }

    @Test
    public void findExistentComponentByIdTest() {
        //GIVEN
        Long componentId = 1L;
        Component expectedComponent = new Component();
        expectedComponent.setId(componentId);
        when(componentRepository.findById(componentId)).thenReturn(Optional.of(expectedComponent));

        //WHEN
        Component component = componentService.findById(componentId);

        //THEN
        assertEquals(expectedComponent, component);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findNonexistentComponentByIdTest() {
        //GIVEN
        Long componentId = 1L;
        when(componentRepository.findById(componentId)).thenReturn(Optional.empty());

        //WHEN
        componentService.findById(componentId);
    }
}