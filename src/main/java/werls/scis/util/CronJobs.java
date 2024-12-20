package werls.scis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.service.ApplyFromSericeImpl;
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

    @Autowired
    ApplyFromSericeImpl applyFromSerice;

    @Scheduled(cron = "0 0/10 *  * * ?")
    public void checkingCompetitionStatus() {

        logger.info("  更新竞赛状态");
        competitionService.upStatus();

        competitionService.upStatusA();
    }

    @Scheduled(cron = "0 0/10 *  * * ?")
    public void upScore() {
        logger.info("更新成绩分类");
        List<ScisCompetition> competitionList = competitionService.findAll();
        for (ScisCompetition competition : competitionList) {
            List<ScisApplyFrom> applyFroms = applyFromSerice.findBySocreDesc(competition.getId());
            boolean f = applyFroms.size() < competition.getFirstPrize();
            boolean s = applyFroms.size() < competition.getSecondPrize();
            boolean t = applyFroms.size() < competition.getThirdPrize();
            applyFromSerice.update(
                    applyFroms.subList(0,
                            f ? applyFroms.size() - 1 : competition.getFirstPrize() - 1),
                    "一等奖");
            if (!f) {
                applyFromSerice.update(
                        applyFroms.subList(
                                competition.getFirstPrize(),
                                s ? applyFroms.size() - 1 : competition.getSecondPrize() - 1),
                        "二等奖");
            }
            if (!s) {
                applyFromSerice.update(
                        applyFroms.subList(
                                competition.getSecondPrize(),
                                t ? applyFroms.size() - 1 : competition.getThirdPrize() - 1),
                        "三等奖");
            }
        }

    }
}
