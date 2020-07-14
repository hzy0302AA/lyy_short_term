package cn.edu.lyy.domin.dao;

import cn.edu.lyy.domin.entity.Course;
import cn.edu.lyy.domin.entity.Homework_teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Homework_teacherDao extends JpaRepository<Homework_teacher,Long> {
    @Query(value = "select * from homework_teacher where homework_name = :homework_name",nativeQuery = true)
    List<Homework_teacher> findHomeworkByHomeworkName(@Param("homework_name")String homework_name);

    @Query(value = "select * from homework_teacher where course_id = :course_id",nativeQuery = true)
    List<Homework_teacher> findAllHomeworkByCourse_id(@Param("course_id")Long course_id);
}
