package cn.edu.lyy.service;

import cn.edu.lyy.domin.entity.Submit_student;

public interface Submit_studentService {

    Submit_student findByhomework_idANDname(Long homework_id,String student_name);

    void addSubmit_student(Submit_student submit_student);

    Submit_student findSubmit_studentById(Long submit_student_id);

    void correctHomework(Submit_student submit_student);

}
