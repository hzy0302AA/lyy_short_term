package cn.edu.lyy.domin.entity;

public class ApplyCourse {

    private Long stu_course_id;
    private Long student_id;
    private String student_name;
    private String status;

    public Long getStu_course_id() {
        return stu_course_id;
    }

    public void setStu_course_id(Long stu_course_id) {
        this.stu_course_id = stu_course_id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
