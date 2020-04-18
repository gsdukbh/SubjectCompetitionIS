package werls.scis.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月21日 16:27
 */
@Data
public class DateScoreToExcel {
    @ExcelProperty({"竞赛信息", "竞赛id"})
    private Integer competitionId;
    @ExcelProperty({"竞赛信息", "竞赛名称"})
    private String competitionName;

    @ExcelProperty({"学生信息", "名字"})
    private String name;

    @ExcelProperty({"学生信息", "学号"})
    private String login;

    @ExcelProperty({"学生信息", "班级"})
    private String className;
    @ExcelProperty({"学生信息", "专业"})
    private String majorName;
    @ExcelProperty({"学生信息", "学院"})
    private String collegeName;
    @ExcelProperty({"学生信息", "性别"})
    private String sex;
    @ExcelProperty({"学生信息", "手机号码"})
    private String phone;
    @ExcelProperty({"学生信息", "身份证号码"})
    private String identity;
    @ExcelProperty({"学生信息", "电子邮箱"})
    private String email;

    @ExcelProperty({"成绩", "分数"})
    private Integer score;
    @ExcelProperty({"成绩", "排名"})
    private String grades;
}
