package werls.scis.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.CompetitionServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.aop
 * @Description: TODO
 * @date Date : 2020年05月29日 21:40
 */
@Aspect
@Component
public class ScoreAop {

    @Autowired
    ApplyFromSericeImpl applyFromSerice;

    @Autowired
    CompetitionServiceImpl competitionService;

    @Pointcut(value = "@annotation(werls.scis.aop.ScoreAopInterface)")
    public void score() {
    }

    @Before("score()")
    public void update() {
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
