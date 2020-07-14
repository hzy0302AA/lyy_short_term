package cn.edu.lyy.domin.dao;

import cn.edu.lyy.domin.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentDao extends JpaRepository<Student,Long> {

    @Query("from Student u where u.student_name=:student_name")
    Student findStudentByName(@Param("student_name")String student_name);
}
