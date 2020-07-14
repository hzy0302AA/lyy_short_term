package cn.edu.lyy.controller;

import cn.edu.lyy.common.HashKit;
import cn.edu.lyy.common.R;
import cn.edu.lyy.domin.entity.*;
import cn.edu.lyy.service.Homework_teacherService;
import cn.edu.lyy.service.StudentService;
import cn.edu.lyy.service.Submit_studentService;
import cn.edu.lyy.service.TeacherService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/teachers")
@Api(value = "教师",tags = "教师")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private Homework_teacherService homework_teacherService;
    @Autowired
    private Submit_studentService submit_studentService;


    @RequestMapping(value = "/findTeacherByName",method = RequestMethod.GET)
    public R<Teacher> findTeacherByName(@RequestParam String teacher_name){

        return R.data(teacherService.findTeacherByName(teacher_name));
    }

    @RequestMapping(value = "/createTeacher",method = RequestMethod.POST)
    public R<String> createTeacher(@Valid @ModelAttribute Teacher teacher){


        Teacher srcteacher=teacherService.findTeacherByName(teacher.getTeacher_name());
        if(srcteacher!=null){
            return R.fail("同名教师已存在");
        }
        teacher.setSalt(HashKit.generateSalt(8));
        teacher.setPassword(HashKit.md5(teacher.getPassword()+teacher.getSalt()));
        teacherService.createteacher(teacher);

        return R.success("success");
    }

    @RequestMapping(value = "/checkLogin_teacher",method = RequestMethod.GET)
    public R<String> checkLogin_teacher(@RequestParam String teacher_name,@RequestParam String password){

        Teacher teacher = teacherService.findTeacherByName(teacher_name);
        if(teacher==null){
            return R.fail("教师不存在！");
        }
        if (teacher.getPassword().equals(HashKit.md5(password+teacher.getSalt()))){
            System.out.println("success");
            teacher.setPassword("***");
            return R.data(200,teacher.getTeacher_name(),"登录成功");
        }else{
            return R.fail(404,"密码错误");
        }

    }

    @RequestMapping(value = "/findApplyListByCourse_id",method = RequestMethod.GET)
    public R<List<ApplyCourse>> findApplyListByCourse_id(@RequestParam Long course_id){
        try{
            List<Stu_course> stu_courseList = teacherService.findAllStu_courseByCourse_id(course_id);
            List<ApplyCourse> applyCourseList = new ArrayList<>();

            for(int i=0;i<stu_courseList.size();i++){
                if(stu_courseList.get(i).getStauts().equals("申请中")){
                    ApplyCourse applyCourse = new ApplyCourse();
                    applyCourse.setStu_course_id(stu_courseList.get(i).getId());
                    applyCourse.setStudent_id(stu_courseList.get(i).getStudent_id());

                    Student student = studentService.findStudentById(stu_courseList.get(i).getStudent_id());
                    applyCourse.setStudent_name(student.getStudent_name());

                    applyCourse.setStatus(stu_courseList.get(i).getStauts());
                    applyCourseList.add(applyCourse);
                }
            }
            return R.data(applyCourseList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/updateApplyCourse",method = RequestMethod.GET)
    public R<String> updateApplyCourse(@RequestParam Long stu_course_id,@RequestParam String status){
        try{
            Stu_course stu_course = teacherService.findStu_courseById(stu_course_id);
            stu_course.setStauts(status);
            teacherService.updateApply(stu_course);
            return R.success("success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.fail("fail");
    }

    @RequestMapping(value = "/addHomework",method = RequestMethod.POST)
    public R<String> addHomework(HttpServletRequest httpServletRequest,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("homework_name") String homework_name,
                                 @RequestParam("begin_time") String begin_time,@RequestParam("finish_time") String finish_time,
                                 @RequestParam("course_id") Long course_id) throws IOException, ParseException {


        System.out.println("[文件类型] - [{}]"+file.getContentType());
        System.out.println("[文件名称] - [{}]"+file.getOriginalFilename());
        System.out.println("[文件大小] - [{}]"+file.getSize());

        UUID uuid = UUID.randomUUID();

        String file_newName = uuid + file.getOriginalFilename();

        file.transferTo(new File("F:\\lyy\\homework_teacher\\"+file_newName));


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式




        java.util.Date begin = format.parse(begin_time);
        java.sql.Timestamp beginSQL = new java.sql.Timestamp(begin.getTime());
        java.util.Date finish = format.parse(finish_time);
        java.sql.Timestamp finishSQL = new java.sql.Timestamp(finish.getTime());

        Homework_teacher homework_teacher = new Homework_teacher();
        homework_teacher.setHomework_name(homework_name);
        homework_teacher.setBegin_time(beginSQL);
        homework_teacher.setFinish_time(finishSQL);
        homework_teacher.setCourse_id(course_id);
        homework_teacher.setUrl("F:\\lyy\\homework_teacher\\"+file_newName);
        homework_teacher.setFile_name(file_newName);
        homework_teacherService.createHomework(homework_teacher);

        return R.success("success");



    }

    @RequestMapping(value = "/correctHomework",method = RequestMethod.GET)
    public R<String> correctHomework(@RequestParam Long submit_student_id,@RequestParam double grade){
        try{
            Submit_student submit_student = submit_studentService.findSubmit_studentById(submit_student_id);
            submit_student.setGrade(grade);
            submit_student.setStatus("已评分");
            submit_studentService.correctHomework(submit_student);
            return R.success("success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.fail("fail");
    }





}
