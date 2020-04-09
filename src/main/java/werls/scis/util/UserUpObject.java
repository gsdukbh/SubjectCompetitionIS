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
@Data
public class UserUpObject {

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

}
