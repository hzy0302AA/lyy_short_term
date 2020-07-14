package cn.edu.lyy.service;

import cn.edu.lyy.domin.entity.Stu_course;
import cn.edu.lyy.domin.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher findTeacherByName(String teacher_name);

    void createteacher(Teacher teacher);

    List<Stu_course> findAllStu_courseByCourse_id(Long course_id);

    Stu_course findStu_courseById(Long stu_course_id);

    void updateApply(Stu_course stu_course);
}
