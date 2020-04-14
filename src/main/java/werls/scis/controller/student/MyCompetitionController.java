package werls.scis.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.dao.pojo.ScisTeamApply;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.CompetitionServiceImpl;
import werls.scis.service.UserService;

import javax.validation.constraints.Email;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年04月14日 17:11
 */
@RestController
@RequestMapping("/student/Competition")
public class MyCompetitionController {
    @Autowired
    CompetitionServiceImpl service;
    @Autowired
    UserService userService;

    @PostMapping("/my/join/{id}")
    public Map<String, Object> findMyJoinA(@PathVariable Integer id,
                                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "20") Integer size,
                                           @RequestParam(name = "name", defaultValue = "") String name) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<ScisCompetition> competitionList = new ArrayList<>(service.findApplyUser(id, name, page, size));
        competitionList.addAll(service.findByApplyAndUserTeam(id, name, page, size));
        res.put("status", 200);
        res.put("data", competitionList);
        res.put("totalElements", competitionList.size());
        return res;

    }


    @PostMapping("/my/{id}")
    public Map<String, Object> findMyJoin(@PathVariable Integer id,
                                          @RequestParam(name = "page", defaultValue = "0") Integer page,
                                          @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisUser> userOptional = userService.findById(id);
        List<ScisCompetition> competitionList = new ArrayList<>();
        if (userOptional.isPresent()) {
            ScisUser user = userOptional.get();
            if (user.getApplyFroms() != null) {
                for (ScisApplyFrom apply : user.getApplyFroms()) {
                    competitionList.add(apply.getCompetition());
                }
            }
            if (user.getTeamApplies() != null) {
                for (ScisTeamApply teamApply : user.getTeamApplies()) {
                    competitionList.add(teamApply.getCompetition());
                }
            }
            /*升序*/
//            competitionList.sort(Comparator.comparing(ScisCompetition::getStartTime));
            /*降序*/
            competitionList.sort((ScisCompetition a, ScisCompetition b) -> b.getStartTime().compareTo(a.getStartTime()));
            int listSize = competitionList.size();
            int toIndex = 0;
            int start = size * page;
            if (start < listSize) {
                if (size > listSize) {
                    toIndex = listSize;
                } else {
                    toIndex = start + size;
                }
                res.put("data", competitionList.subList(start, toIndex));
            } else {
                res.put("data", new ArrayList<ScisCompetition>());
            }

        }
//        Collections.binarySearch(competitionList,)
        return res;
    }
}
