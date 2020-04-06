package werls.scis.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import werls.scis.service.UserServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月06日 22:06
 */
public class ExcelToObject extends AnalysisEventListener<UserUpObject> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final int BATCH_COUNT = 3000;

    List<UserUpObject> list = new ArrayList<>();

    UserServiceImpl userService;

    public ExcelToObject(UserServiceImpl userService) {
        this.userService = userService;
    }

    public ExcelToObject() {

    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param userUpObject    UserUpObject
     * @param analysisContext AnalysisContext
     */
    @Override
    public void invoke(UserUpObject userUpObject, AnalysisContext analysisContext) {
        logger.info("解析到一条数据:{}", JSON.toJSONString(userUpObject));

    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext AnalysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("所有数据解析完成！");
    }
}


