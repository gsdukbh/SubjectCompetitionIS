package werls.scis.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import werls.scis.service.UserServiceImpl;
import werls.scis.webSocket.WebSocket;

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

    int i = 0;

    UserServiceImpl userService;

    Integer adminUser;


    WebSocket webSocket;

    public ExcelToObject(UserServiceImpl userService) {
        this.userService = userService;
    }

    public ExcelToObject(Integer adminUser) {
        this.adminUser = adminUser;
    }

    public ExcelToObject() {

    }
    public ExcelToObject(Integer adminUser,WebSocket webSocket ) {
        this.webSocket=webSocket;
        this.adminUser = adminUser;
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
        list.add(userUpObject);
        webSocket.sendOneMessage(adminUser.toString(),JSON.toJSONString(userUpObject));
        System.out.println(i++);
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


