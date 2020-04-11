package werls.scis.util;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月06日 21:23
 */
//@Data
public class UserUpObject {

    @ExcelProperty(value = "状态")
    private String status;
    @ExcelProperty(value = "密码")
    private String password;

    @ExcelProperty(value = "id")
    private Integer id;

    @ExcelProperty(value = "学号")
    private String login;

    @ExcelProperty(value = "名字")
    private String name;
    @ExcelProperty(value = "性别")
    private String sex;
    @ExcelProperty(value = "院系")
    private String college;
    @ExcelProperty(value = "专业")
    private String majorName;
    @ExcelProperty(value = "培养层次")
    private String level;
    @ExcelProperty(value = "班级")
    private String className;
    @ExcelProperty(value = "身份证号")
    private String identity;
    @ExcelProperty(value = "手机号码")
    private String phone;
    @ExcelProperty(value = "邮箱")
    private String email;
    @ExcelProperty(value = "身份")
    private String role;

    @Override
    public String toString() {
        return "UserUpObject{" +
                "status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", college='" + college + '\'' +
                ", majorName='" + majorName + '\'' +
                ", level='" + level + '\'' +
                ", className='" + className + '\'' +
                ", identity='" + identity + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
