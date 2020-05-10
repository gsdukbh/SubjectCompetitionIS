package werls.scis.util;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月19日 17:13
 */
@Data
public class Score {
    @ExcelProperty("竞赛id")
    private Integer competitionId;
    @ExcelProperty("竞赛名称")
    private String competitionName;
    @ExcelProperty("学号")
    private String login;
    @ExcelProperty("名字")
    private String name;
    @ExcelProperty("分数")
    private Integer score;
//    @ExcelProperty("排名")
//    private String grades;
}
