package cn.edu.lyy.domin.dao;

import cn.edu.lyy.domin.entity.Course;
import cn.edu.lyy.domin.entity.Submit_student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Submit_studentDao extends JpaRepository<Submit_student,Long> {

    @Query(value = "select * from submit_student where homework_id = :homework_id " +
            "and student_name = :student_name",nativeQuery = true)
    Submit_student findByhomework_idANDname(@Param("homework_id")Long homework_id,
                                            @Param("student_name")String student_name);

}
