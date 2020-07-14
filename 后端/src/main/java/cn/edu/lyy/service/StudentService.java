package cn.edu.lyy.service;

import cn.edu.lyy.domin.entity.Stu_course;
import cn.edu.lyy.domin.entity.Student;

import java.util.List;

public interface StudentService {

    Student findStudentByName(String student_name);

    Student findStudentById(Long student_id);

    void createStudent(Student student);

    void applyCourse(Stu_course stu_course);

    List<Stu_course> findAllStu_coursesByStudent_id(Long student_id);

}
