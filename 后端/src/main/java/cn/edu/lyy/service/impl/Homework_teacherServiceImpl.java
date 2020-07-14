package cn.edu.lyy.service.impl;


import cn.edu.lyy.domin.dao.Homework_teacherDao;
import cn.edu.lyy.domin.entity.Homework_teacher;
import cn.edu.lyy.service.Homework_teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Homework_teacherServiceImpl implements Homework_teacherService {

    @Autowired
    private Homework_teacherDao homework_teacherDao;

    @Override
    public void createHomework(Homework_teacher homework) {
        this.homework_teacherDao.save(homework);
    }

    @Override
    public List<Homework_teacher> findAllHomeworkByCourse_id(Long course_id) {
        return this.homework_teacherDao.findAllHomeworkByCourse_id(course_id);
    }

    @Override
    public Homework_teacher findHomeworkById(Long homework_teacher_id) {
        return this.homework_teacherDao.findById(homework_teacher_id).get();
    }
}
