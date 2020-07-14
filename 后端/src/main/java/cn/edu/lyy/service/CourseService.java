package cn.edu.lyy.service;

import cn.edu.lyy.domin.entity.Course;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseService {

    void createCourse(Course course);

    void updateCourse(Course course);

    Course findCoursesByCourseName(String course_name);

    List<Course> findCoursesByTeacherName(String teacher_name);

    Course findCoursesById(Long id);



    List<Course> findAllCourses();


}
