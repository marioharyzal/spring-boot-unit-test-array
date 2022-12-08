package com.enigmacamp.hellospring.service;

import com.enigmacamp.hellospring.model.Course;
import com.enigmacamp.hellospring.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {
    //    @TestConfiguration
//    static class CourseServiceConfiguration {
//
//        @Bean
//        public CourseService courseService() {
//            return new CourseServiceImpl();
//        }
//    }

    @Mock
    CourseRepository mockCourseRepository;
    CourseService courseService;

    @BeforeEach
    void setup() {
        courseService = new CourseServiceImpl(mockCourseRepository);
    }

    @Test
    void itShould_ReturnListOfCourse_when_CourseServiceListCalled() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("Dummy link");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");

        List<Course> courseListDummy = new ArrayList<>();
        courseListDummy.add(dummyCourse);

        when(mockCourseRepository.getAll()).thenReturn(courseListDummy);
        List<Course> actualCourse = courseService.list();
        assertEquals(1, actualCourse.size());
    }

    @Test
    void itShould_ThrowException_When_CourseServiceListGetError() throws Exception {
        when(mockCourseRepository.getAll()).thenThrow(Exception.class);
        assertThrows(RuntimeException.class, () -> courseService.list());
    }

    @Test
    void itShould_ReturnCourse_When_CourseServiceCreate() throws Exception {
        //given
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("Dummy link");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");
        when(mockCourseRepository.create(any())).thenReturn(dummyCourse);

        //when
        Course actualCourse = courseService.create(dummyCourse);

        //then(Actual)
        assertEquals(dummyCourse.getCourseId(), actualCourse.getCourseId());
    }

    @Test
    void itShould_SuccessfullyDeleteCourse_When_CourseServiceDelete() throws Exception {
        //when
        doNothing().when(mockCourseRepository).delete(anyString());

        //then
        assertDoesNotThrow(() -> courseService.delete("1"));
        verify(mockCourseRepository, times(1)).delete("1");
    }

    @Test
    void itShould_ThrowError_When_CourseServiceCreate() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");

        when(mockCourseRepository.create(any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> courseService.create(dummyCourse));
    }

    @Test
    void itShould_ReturnCourse_When_CourseServiceGet() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setCourseId("1");
        dummyCourse.setLink("link dummy");
        dummyCourse.setDescription("dummy desc");
        dummyCourse.setTitle("dummy title");

        when(mockCourseRepository.findById("1")).thenReturn(Optional.of(dummyCourse));

        Course course = courseService.get("1");

        assertEquals(dummyCourse, course);
    }
}
