package cn.edu.lyy.controller;

import cn.edu.lyy.common.HashKit;
import cn.edu.lyy.common.R;
import cn.edu.lyy.domin.entity.Course;
import cn.edu.lyy.domin.entity.Teacher;
import cn.edu.lyy.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "*")//跨域
@RestController
@RequestMapping(value = "/course")
@Api(value = "课程",tags = "课程")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/createCourse",method = RequestMethod.POST)
    public R<String> createCourse(@Valid @ModelAttribute Course course){

        Course srccourse=courseService.findCoursesByCourseName(course.getCourse_name());

        if(srccourse!=null){
            return R.fail("同名课程已存在");
        }

        courseService.createCourse(course);

        return R.success("success");
    }

    @RequestMapping(value = "/findCoursesByTeacherName",method = RequestMethod.GET)
    public R<List<Course>> findCoursesByTeacherName(@RequestParam String teacher_name){

        System.out.println(teacher_name);
        List<Course> courseList = courseService.findCoursesByTeacherName(teacher_name);


        return R.data(courseList);
    }

    @RequestMapping(value = "/findCoursesById",method = RequestMethod.GET)
    public R<Course> findCoursesById(@RequestParam Long courseId){
        Course course = courseService.findCoursesById(courseId);
        return R.data(course) ;
    }



    @RequestMapping(value = "/findAllCourses",method = RequestMethod.GET)
    public R<List<Course>> findAllCourses(){
        List<Course> courseList = courseService.findAllCourses();
        return R.data(courseList);
    }

}
