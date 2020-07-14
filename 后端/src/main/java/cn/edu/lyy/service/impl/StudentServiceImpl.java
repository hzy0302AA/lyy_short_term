package cn.edu.lyy.service.impl;

import cn.edu.lyy.domin.dao.Stu_CourseDao;
import cn.edu.lyy.domin.dao.StudentDao;
import cn.edu.lyy.domin.entity.Stu_course;
import cn.edu.lyy.domin.entity.Student;
import cn.edu.lyy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private Stu_CourseDao stu_courseDao;


    @Override
    public Student findStudentByName(String student_name) {

        return this.studentDao.findStudentByName(student_name);
    }

    @Override
    public Student findStudentById(Long student_id) {
        return this.studentDao.findById(student_id).get();
    }

    @Override
    public void createStudent(Student student) {
        this.studentDao.save(student);

    }

    @Override
    public void applyCourse(Stu_course stu_course) {
        this.stu_courseDao.save(stu_course);
    }

    @Override
    public List<Stu_course> findAllStu_coursesByStudent_id(Long student_id) {
        return this.stu_courseDao.findAllByStudent_id(student_id);
    }
}
