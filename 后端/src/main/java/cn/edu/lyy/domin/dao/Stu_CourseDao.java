package cn.edu.lyy.domin.dao;

import cn.edu.lyy.domin.entity.Stu_course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Stu_CourseDao extends JpaRepository<Stu_course,Long> {

    @Query(value = "select * from stu_course where student_id = :student_id",nativeQuery = true)
    List<Stu_course> findAllByStudent_id(@Param("student_id")Long student_id);

    @Query(value = "select * from stu_course where course_id = :course_id",nativeQuery = true)
    List<Stu_course> findAllByCourse_id(@Param("course_id")Long course_id);

}
