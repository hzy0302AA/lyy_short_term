package cn.edu.lyy.controller;

import cn.edu.lyy.common.R;
import cn.edu.lyy.domin.entity.*;
import cn.edu.lyy.service.Homework_teacherService;
import cn.edu.lyy.service.StudentService;
import cn.edu.lyy.service.Submit_studentService;
import cn.edu.lyy.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/homework")
@Api(value = "作业",tags = "作业")
public class HomeworkController {

    @Autowired
    private Homework_teacherService homework_teacherService;
    @Autowired
    private Submit_studentService submit_studentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/findAllHomeworkByCourse_id",method = RequestMethod.GET)
    public R<List<Homework_teacher>> findAllHomeworkByCourse_id(@RequestParam Long course_id){
        try{
            List<Homework_teacher> homework_teacherList = homework_teacherService.findAllHomeworkByCourse_id(course_id);
            return R.data(homework_teacherList);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/findHomeworkById",method = RequestMethod.GET)
    public R<Homework_teacher> findHomeworkById(@RequestParam Long homework_teacher_id){
        try{
            Homework_teacher homework_teacher = homework_teacherService.findHomeworkById(homework_teacher_id);
            return R.data(homework_teacher);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/findAllHomework_stuList",method = RequestMethod.GET)
    public R<List<Homework_stuList>> findAllHomework_stuList(@RequestParam Long course_id,@RequestParam String student_name){
        try{
            List<Homework_teacher> homework_teacherList = homework_teacherService.findAllHomeworkByCourse_id(course_id);

            List<Homework_stuList> homework_stuListList = new ArrayList<>();

            for(int i=0;i<homework_teacherList.size();i++){
                Homework_teacher homework_teacher = homework_teacherList.get(i);
                Homework_stuList homework_stuList = new Homework_stuList();

                Submit_student submit_student = submit_studentService.findByhomework_idANDname(homework_teacher.getId(),student_name);

                Timestamp finish_time = homework_teacher.getFinish_time();
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                homework_stuList.setHomework_teacher_id(homework_teacher.getId());
                homework_stuList.setHomework_name(homework_teacher.getHomework_name());
                homework_stuList.setBegin_time(homework_teacher.getBegin_time());
                homework_stuList.setFinish_time(homework_teacher.getFinish_time());


                if(submit_student!=null){
                    homework_stuList.setStatus(submit_student.getStatus());
                    homework_stuList.setGrade(submit_student.getGrade());
                    homework_stuListList.add(homework_stuList);
                }else if(submit_student == null && finish_time.getTime()>=currentTime.getTime()){
                    homework_stuList.setStatus("未提交");
                    homework_stuList.setGrade(0);
                    homework_stuListList.add(homework_stuList);
                }else{
                    homework_stuList.setGrade(0);
                    homework_stuList.setStatus("已到期");
                    homework_stuListList.add(homework_stuList);
                }

            }
            return R.data(homework_stuListList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/findAllHomework_submit",method = RequestMethod.GET)
    public R<List<Homework_submit>> findAllHomework_submit(@RequestParam Long course_id,@RequestParam Long homework_id){
        try{
            //System.out.println(course_id);
            List<Stu_course> stu_courseList = teacherService.findAllStu_courseByCourse_id(course_id);
            List<Homework_submit> homework_submitList = new ArrayList<>();
            for(int i = 0;i<stu_courseList.size();i++){
                Stu_course stu_course = stu_courseList.get(i);
                if(stu_course.getStauts().equals("已通过")){
                    //System.out.println("11");
                    Student student = studentService.findStudentById(stu_course.getStudent_id());
                    Submit_student submit_student =
                            submit_studentService.findByhomework_idANDname(homework_id,student.getStudent_name());

                    Homework_submit homework_submit = new Homework_submit();
                    homework_submit.setStudent_id(student.getId());
                    homework_submit.setStudent_name(student.getStudent_name());

                    if(submit_student!=null){
                        homework_submit.setStatus(submit_student.getStatus());
                        homework_submit.setGrade(submit_student.getGrade());
                        homework_submit.setSubmit_time(submit_student.getSubmit_time());
                        homework_submit.setSubmit_student_id(submit_student.getId());
                    }else{
                        homework_submit.setStatus("未提交");
                        homework_submit.setGrade(0);
                        homework_submit.setSubmit_time(null);
                        homework_submit.setSubmit_student_id(null);
                    }
                    homework_submitList.add(homework_submit);
                }
            }
            return R.data(homework_submitList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    @RequestMapping(value = "/downloadHomework", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadHomework(@RequestParam Long id) throws IOException {
        System.out.println(id);//homework_teacher_id

        Homework_teacher homework_teacher = homework_teacherService.findHomeworkById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //设置文件名
        headers.setContentDispositionFormData("attachment", homework_teacher.getFile_name());
        headers.set("Content-Disposition", "attachment;filename=" + homework_teacher.getFile_name());
        String file = homework_teacher.getUrl();

        //把文件转成字节数组
        File byteFile = new File(file);
        byte[] bytes = Files.readAllBytes(byteFile.toPath());

        //返回
        return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/downloadSubmit", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadSubmit(@RequestParam Long submit_student_id) throws IOException {
        System.out.println(submit_student_id);//submit_student_id

        //Homework_teacher homework_teacher = homework_teacherService.findHomeworkById(id);
        Submit_student submit_student = submit_studentService.findSubmit_studentById(submit_student_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //设置文件名
        headers.setContentDispositionFormData("attachment", submit_student.getFile_name());
        headers.set("Content-Disposition", "attachment;filename=" + submit_student.getFile_name());
        String file = submit_student.getUrl();

        //把文件转成字节数组
        File byteFile = new File(file);
        byte[] bytes = Files.readAllBytes(byteFile.toPath());

        //返回
        return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
    }







}
