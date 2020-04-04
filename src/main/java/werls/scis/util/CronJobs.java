package werls.scis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.service.CompetitionServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月04日 10:22
 */
@Component
public class CronJobs {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CompetitionServiceImpl competitionService;

    @Scheduled(cron = "0 0/10 *  * * ?")
    public void checkingCompetitionStatus() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(sdf.format(new Date())+"  更新竞赛状态");
       competitionService.upStatus();

       competitionService.upStatusA();
    }

}
