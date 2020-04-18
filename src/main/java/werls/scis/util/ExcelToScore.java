package werls.scis.util;

import com.alibaba.excel.context.AnalysisContext;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.UserService;
import werls.scis.service.WorksServiceImpl;
import werls.scis.webSocket.WebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月19日 17:21
 */
public class ExcelToScore extends AnalysisEventListener<Score> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int BATCH_COUNT = 1000;

    List<Score> list = new ArrayList<>();


    Integer userId;
    Integer competitionId;
    WebSocket webSocket;
    Tools tools;

    public ExcelToScore(Integer userId,
                        Integer competitionId,
                        WebSocket webSocket, Tools tools) {
        this.webSocket = webSocket;
        this.competitionId = competitionId;
        this.userId = userId;
        this.tools = tools;
    }

    @Override
    public void invoke(Score score, AnalysisContext analysisContext) {
        logger.info("解析到一条数据:{}", JSON.toJSONString(score));
        Map<String, Object> send = new HashMap<>();
        send.put("messages", "解析到一条数据");
        send.put("data", score);
        webSocket.sendOneMessage(userId.toString(), JSON.toJSONString(send));
        list.add(score);
        if (list.size() >= BATCH_COUNT) {
            tools.saveScore(list, competitionId, userId);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("数据解析完成！");
        tools.saveScore(list, competitionId, userId);
    }

}
