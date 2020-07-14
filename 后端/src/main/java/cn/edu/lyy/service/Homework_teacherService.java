package cn.edu.lyy.service;

import cn.edu.lyy.domin.entity.Homework_teacher;

import java.util.List;

public interface Homework_teacherService {
    void createHomework(Homework_teacher homework_teacher);

    List<Homework_teacher> findAllHomeworkByCourse_id(Long course_id);

    Homework_teacher findHomeworkById(Long homework_teacher_id);
}
