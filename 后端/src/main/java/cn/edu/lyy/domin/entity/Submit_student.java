package cn.edu.lyy.domin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity()
@ApiModel(value = "学生提交作业",description = "学生提交作业")
@Table(name="submit_student")
public class Submit_student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")

    private Long id;
    private Long homework_id;
    private String student_name;
    private String file_name;
    private String url;
    private String status;
    private double grade;
    private Timestamp submit_time;

    public Timestamp getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Timestamp submit_time) {
        this.submit_time = submit_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(Long homework_id) {
        this.homework_id = homework_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String fil_name) {
        this.file_name = fil_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
