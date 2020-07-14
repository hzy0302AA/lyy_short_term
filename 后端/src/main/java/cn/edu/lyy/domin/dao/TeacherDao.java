package cn.edu.lyy.domin.dao;

import cn.edu.lyy.domin.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherDao extends JpaRepository<Teacher,Long> {

    @Query("from Teacher u where u.teacher_name=:teacher_name")
    Teacher findTeacherByName(@Param("teacher_name")String teacher_name);
}
