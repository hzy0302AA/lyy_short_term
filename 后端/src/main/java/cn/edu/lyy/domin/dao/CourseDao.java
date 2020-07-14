package cn.edu.lyy.domin.dao;

import cn.edu.lyy.domin.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDao extends JpaRepository<Course,Long> {

    @Query(value = "select * from course where teacher_name = :teacher_name",nativeQuery = true)
    List<Course> findCoursesByTeacherName(@Param("teacher_name")String teacher_name);

    @Query(value = "select * from course where course_name = :course_name",nativeQuery = true)
    Course findCoursesByCourseName(@Param("course_name")String course_name);








}
