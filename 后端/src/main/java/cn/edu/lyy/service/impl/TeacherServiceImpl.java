package cn.edu.lyy.service.impl;

import cn.edu.lyy.domin.dao.Stu_CourseDao;
import cn.edu.lyy.domin.dao.TeacherDao;
import cn.edu.lyy.domin.entity.Stu_course;
import cn.edu.lyy.domin.entity.Teacher;
import cn.edu.lyy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private Stu_CourseDao stu_courseDao;

    @Override
    public Teacher findTeacherByName(String teacher_name) {
        return this.teacherDao.findTeacherByName(teacher_name);
    }

    @Override
    public void createteacher(Teacher teacher) {
        this.teacherDao.save(teacher);
    }

    @Override
    public List<Stu_course> findAllStu_courseByCourse_id(Long course_id) {
        return this.stu_courseDao.findAllByCourse_id(course_id);
    }

    @Override
    public Stu_course findStu_courseById(Long stu_course_id) {
        return this.stu_courseDao.findById(stu_course_id).get();
    }

    @Override
    public void updateApply(Stu_course stu_course) {
        this.stu_courseDao.save(stu_course);
    }
}
