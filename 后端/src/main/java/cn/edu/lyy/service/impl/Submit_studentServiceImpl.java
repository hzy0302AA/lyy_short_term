package cn.edu.lyy.service.impl;

import cn.edu.lyy.domin.dao.Submit_studentDao;
import cn.edu.lyy.domin.entity.Submit_student;
import cn.edu.lyy.service.Submit_studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Submit_studentServiceImpl implements Submit_studentService {

    @Autowired
    private Submit_studentDao submit_studentDao;

    @Override
    public Submit_student findByhomework_idANDname(Long homework_id, String student_name) {
        return this.submit_studentDao.findByhomework_idANDname(homework_id,student_name);
    }

    @Override
    public void addSubmit_student(Submit_student submit_student) {
        this.submit_studentDao.save(submit_student);
    }

    @Override
    public Submit_student findSubmit_studentById(Long submit_student_id) {
        return this.submit_studentDao.findById(submit_student_id).get();
    }

    @Override
    public void correctHomework(Submit_student submit_student) {
        this.submit_studentDao.save(submit_student);
    }
}
