package cn.edu.lyy.service.impl;

import cn.edu.lyy.domin.dao.CourseDao;
import cn.edu.lyy.domin.dao.TeacherDao;
import cn.edu.lyy.domin.entity.Course;
import cn.edu.lyy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public void createCourse(Course course) {
        this.courseDao.save(course);
    }

    @Override
    public void updateCourse(Course course) {
        this.courseDao.save(course);
    }

    @Override
    public Course findCoursesByCourseName(String course_name) {
        return this.courseDao.findCoursesByCourseName(course_name);
    }

    @Override
    public List<Course> findCoursesByTeacherName(String teacher_name) {
        return this.courseDao.findCoursesByTeacherName(teacher_name);
    }

    @Override
    public Course findCoursesById(Long id) {
        return this.courseDao.findById(id).get();
    }



    @Override
    public List<Course> findAllCourses() {
        return this.courseDao.findAll();
    }


}
