package cn.edu.lyy.domin.entity;

import java.sql.Timestamp;

public class Homework_stuList {

    private Long homework_teacher_id;
    private String homework_name;
    private String homework_teacher_file_name;
    private String homework_teacher_url;
    private Timestamp begin_time;
    private Timestamp finish_time;
    private String status;
    private double grade;

    public Long getHomework_teacher_id() {
        return homework_teacher_id;
    }

    public void setHomework_teacher_id(Long homework_teacher_id) {
        this.homework_teacher_id = homework_teacher_id;
    }

    public String getHomework_name() {
        return homework_name;
    }

    public void setHomework_name(String homework_name) {
        this.homework_name = homework_name;
    }

    public String getHomework_teacher_file_name() {
        return homework_teacher_file_name;
    }

    public void setHomework_teacher_file_name(String homework_teacher_file_name) {
        this.homework_teacher_file_name = homework_teacher_file_name;
    }

    public String getHomework_teacher_url() {
        return homework_teacher_url;
    }

    public void setHomework_teacher_url(String homework_teacher_url) {
        this.homework_teacher_url = homework_teacher_url;
    }

    public Timestamp getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Timestamp begin_time) {
        this.begin_time = begin_time;
    }

    public Timestamp getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Timestamp finish_time) {
        this.finish_time = finish_time;
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
}
