package cn.edu.lyy.controller;


import cn.edu.lyy.common.HashKit;
import cn.edu.lyy.common.R;
import cn.edu.lyy.domin.entity.*;
import cn.edu.lyy.service.CourseService;
import cn.edu.lyy.service.StudentService;
import cn.edu.lyy.service.Submit_studentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/students")
@Api(value = "学生",tags = "学生")
public class StuentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private Submit_studentService submit_studentService;

    @RequestMapping(value = "/findStudentByName",method = RequestMethod.GET)
    public R<Student> findStudentByName(@RequestParam String student_name){

        return R.data(studentService.findStudentByName(student_name));
    }

    @RequestMapping(value = "/createStudent",method = RequestMethod.POST)
    public R<String> createStudent(@Valid @ModelAttribute Student student){

        //System.out.println(student.getStudent_name());

        Student srcstudent=studentService.findStudentByName(student.getStudent_name());
        if(srcstudent!=null){
            return R.fail("同名学生已存在");
        }
        student.setSalt(HashKit.generateSalt(8));
        student.setPassword(HashKit.md5(student.getPassword()+student.getSalt()));
        studentService.createStudent(student);

        return R.success("success");
    }

    @RequestMapping(value = "/checkLogin_student",method = RequestMethod.GET)
    public R<String> checkLogin_student(@RequestParam String student_name,@RequestParam String password){

        Student student = studentService.findStudentByName(student_name);
        if(student==null){
            return R.fail("学生不存在！");
        }
        if (student.getPassword().equals(HashKit.md5(password+student.getSalt()))){
            System.out.println("success");
            student.setPassword("***");
            return R.data(200,student.getStudent_name(),"登录成功");
        }else{
            return R.fail(404,"密码错误");
        }

    }

    @RequestMapping(value = "/applyCourse",method = RequestMethod.GET)
    public R<String> applyCourse(@RequestParam Long course_id,@RequestParam String student_name){

        try{
            Student student = studentService.findStudentByName(student_name);

            //验证是否有申请过课程
            List<Stu_course> stu_courseList = studentService.findAllStu_coursesByStudent_id(student.getId());
            for(int i=0;i<stu_courseList.size();i++){
                if(stu_courseList.get(i).getCourse_id()== course_id){
                    return R.fail("已申请过该课程");
                }
            }


            Stu_course stu_course = new Stu_course();



            stu_course.setCourse_id(course_id);
            stu_course.setStudent_id(student.getId());
            stu_course.setStauts("申请中");

            studentService.applyCourse(stu_course);

            return R.success("success");

        }catch (Exception e){
            e.printStackTrace();
        }
        return R.fail("fail");
    }

    @RequestMapping(value = "/findCoursesByStudent_name",method = RequestMethod.GET)
    public R<List<Course>> findCoursesByStudent_name(@RequestParam String student_name){
        try{
            Student student = studentService.findStudentByName(student_name);
            List<Stu_course> stu_courseList = studentService.findAllStu_coursesByStudent_id(student.getId());
            List<Course> courseList = new ArrayList<>();

            for(int i=0;i<stu_courseList.size();i++){
                Course course = new Course();
                if(stu_courseList.get(i).getStauts().equals("已通过")){
                    course = courseService.findCoursesById(stu_courseList.get(i).getCourse_id());
                    courseList.add(course);
                }

            }
            return R.data(courseList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/submitHomework_student",method = RequestMethod.POST)
    public R<String> submitHomework_student(HttpServletRequest httpServletRequest,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("homework_teacher_id") Long homework_teacher_id,
                                 @RequestParam("student_name") String student_name)
            throws IOException, ParseException {


        System.out.println("[文件类型] - [{}]"+file.getContentType());
        System.out.println("[文件名称] - [{}]"+file.getOriginalFilename());
        System.out.println("[文件大小] - [{}]"+file.getSize());

        UUID uuid = UUID.randomUUID();

        String file_newName = uuid + file.getOriginalFilename();

        file.transferTo(new File("F:\\lyy\\homework_student\\"+file_newName));


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式

        Submit_student submit_student = new Submit_student();
        submit_student.setHomework_id(homework_teacher_id);
        submit_student.setStudent_name(student_name);
        submit_student.setFile_name(file_newName);
        submit_student.setUrl("F:\\lyy\\homework_student\\"+file_newName);
        submit_student.setStatus("已提交");
        submit_student.setGrade(0);


        Date date = new Date();
        //得到一个timestamp格式的时间，存入mysql中的时间格式为"yyyy-MM-dd HH:mm:ss"
        Timestamp timestamp = new Timestamp(date.getTime());

        submit_student.setSubmit_time(timestamp);
        submit_studentService.addSubmit_student(submit_student);





        return R.success("success");



    }




}
