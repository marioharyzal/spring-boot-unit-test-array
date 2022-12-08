package com.enigmacamp.hellospring.controller;

import com.enigmacamp.hellospring.model.Course;
import com.enigmacamp.hellospring.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CourseService mockCourseService;

    @MockBean
    ModelMapper modelMapper;

    @Test
    void itShould_ReturnResponseOk_WhenGetAllCourse() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("link dummy");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");

        List<Course> courseListDummy = new ArrayList<>();
        courseListDummy.add(dummyCourse);

        when(mockCourseService.list()).thenReturn(courseListDummy);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(courseListDummy)));
    }

    @Test
    void itShould_ReturnResponseOk_When_CreateCourse() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("link dummy");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");

        when(mockCourseService.create(any())).thenReturn(dummyCourse);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                        post("/courses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dummyCourse))
                ).andExpect(status().isCreated())
                .andExpect(content().json((objectMapper.writeValueAsString(dummyCourse))));
    }

    @Test
    void itShould_ResponseOk_When_GetCourseById() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("link dummy");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");

        when(mockCourseService.get(anyString())).thenReturn(dummyCourse);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(get("/courses/${id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dummyCourse)));
    }

    @Test
    void itShould_ReturnResponseOk_When_GetCourseByKeyword() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("link dummy");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");
        List<Course> courseListDummy = new ArrayList<>();
        courseListDummy.add(dummyCourse);

        when(mockCourseService.getBy(anyString(), anyString())).thenReturn(courseListDummy);

        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("keyword", "title");
        paramsMap.add("value", "123");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(get("/courses").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(courseListDummy)));
    }
}
