package cn.edu.lyy.domin.entity;

import java.sql.Timestamp;

public class Homework_submit {
    private Long student_id;
    private String student_name;
    private String status;
    private double grade;
    private Timestamp submit_time;
    private Long submit_student_id;

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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Timestamp getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Timestamp submit_time) {
        this.submit_time = submit_time;
    }

    public Long getSubmit_student_id() {
        return submit_student_id;
    }

    public void setSubmit_student_id(Long submit_student_id) {
        this.submit_student_id = submit_student_id;
    }
}
