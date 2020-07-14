package cn.edu.lyy.common;

import cn.edu.lyy.domin.entity.Student;
import cn.edu.lyy.domin.entity.Teacher;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.lang.Nullable;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class R<T> implements Serializable {
    private  static final  long seriaVersionUID = 1L;

    private int code;

    private boolean success;
    private T data;
    private T data1;

    private String msg;

    private R(ResultCode resultCode){
        this(resultCode,null,resultCode.getMessage());
    }

    private  R(ResultCode resultCode,String msg){
        this(resultCode,null,msg);
    }

    private R(ResultCode resultCode,T data){
        this(resultCode,data,resultCode.getMessage());
    }

    private R(ResultCode resultCode,T data,String msg){
        this(resultCode.getCode(),data,msg);
    }



    private R(int code,T data,String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code ==code;
    }

    public static boolean isSuccess(@Nullable R<?> result){
        return (Boolean) Optional.ofNullable(result).map((x) ->{return ResultCode.SUCCESS.code== x.code;
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable R<?> result){
        return !isSuccess(result);
    }

    public static final Key KEY = new SecretKeySpec("zucc".getBytes(),
            SignatureAlgorithm.HS512.getJcaName());
    public static <T> R<T> Loginsuccess_teacher(Teacher teacher){
        JwtBuilder builder = Jwts.builder().setHeaderParam("type","JsonWebToken")
                .claim("id",teacher.getId())
                .claim("name",teacher.getTeacher_name())
                .signWith(SignatureAlgorithm.HS512,KEY);
        long expMillis = System.currentTimeMillis() + 3600*1000*24;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(new Date());

        return new R(ResultCode.SUCCESS,builder.compact(),"登陆成功");
    }

    public static <T> R<T> Loginsuccess_student(Student student){
        JwtBuilder builder = Jwts.builder().setHeaderParam("type","JsonWebToken")
                .claim("id",student.getId())
                .claim("name",student.getStudent_name())
                .signWith(SignatureAlgorithm.HS512,KEY);
        long expMillis = System.currentTimeMillis() + 3600*1000*24;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(new Date());

        return new R(ResultCode.SUCCESS,builder.compact(),"登陆成功");
    }


    public static <T> R<T> data(T data){
        return data(data,"操作成功");
    }

    public static <T> R<T> data(T data,String msg){
        return data(200,data,msg);
    }

    public static <T> R<T> data(int code,T data,String msg){
        return new R(code,data,data == null ? "暂无数据" : msg);
    }

    public static <T> R<T> success(String msg){
        return new R(ResultCode.SUCCESS,msg);
    }

    public static <T> R<T> success(ResultCode resultCode){
        return new R(resultCode);
    }

    public static <T> R<T> success(ResultCode resultCode,String msg){
        return  new R(resultCode, msg);
    }

    public static <T> R<T> fail(String msg){
        return new R(ResultCode.FAILURE,msg);
    }

    public static <T> R<T> fail(int code,String msg){
        return new R(code,(Object)null,msg);
    }

    public static <T> R<T> fail(ResultCode resultCode){
        return new R(resultCode);
    }

    public static <T> R<T> fail(ResultCode resultCode,String msg){
        return new R(resultCode,msg);
    }

    public static <T> R<T> status(boolean flag){
        return flag ? success("操作成功") : fail("操作失败");
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess(){
        return this.success;
    }

    public T getData(){
        return this.data;
    }

    public String getMsg(){
        return this.msg;
    }

    public void setCode(final int code){
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(final boolean success){
        this.success = success;
    }

}

