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
 * @date Date : 2020年04月17日 11:45
 */
@Data
public class ApplyFromToExceal {
    @ExcelProperty({"竞赛信息", "竞赛id"})
    private Integer competitionId;
    @ExcelProperty({"竞赛信息", "竞赛名称"})
    private String competitionName;
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty({"报名信息", "申请时间"})
    private Date applyTime;
    @ExcelProperty({"报名信息", "学号"})
    private String name;
    @ExcelProperty({"报名信息", "名字"})
    private String login;
    @ExcelProperty({"报名信息", "班级"})
    private String className;
    @ExcelProperty({"报名信息", "专业"})
    private String majorName;
    @ExcelProperty({"报名信息", "学院"})
    private String collegeName;
    @ExcelProperty({"报名信息", "性别"})
    private String sex;
    @ExcelProperty({"报名信息", "手机号码"})
    private String phone;
    @ExcelProperty({"报名信息", "身份证号码"})
    private String identity;
    @ExcelProperty({"报名信息", "电子邮箱"})
    private String email;
}
