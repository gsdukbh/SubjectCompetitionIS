package werls.scis.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.UserService;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年04月14日 16:29
 */
@RestController
@RequestMapping("/student/personal/apply")
public class PersonalApplyController {
    @Autowired
    ApplyFromSericeImpl service;

    @Autowired
    UserService userService;

    @PostMapping("/join")
    public Map<String, Object> save(@RequestBody ScisApplyFrom applyFrom) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Set<Integer> competitionId = new HashSet<>();
        Optional<ScisUser> userOptional = userService.findById(applyFrom.getScisUser().getId());
        if (userOptional.isPresent()) {
            ScisUser user = userOptional.get();
            if (user.getApplyFroms() != null) {
                for (ScisApplyFrom apply : user.getApplyFroms()) {
                    if (apply.getCompetition() != null) {
                        competitionId.add(apply.getCompetition().getId());
                    }
                }
            }
            if (competitionId.add(applyFrom.getCompetition().getId())) {
                res.put("result", service.save(applyFrom));
                res.put("message", "报名成功");
                res.put("status", 200);
            } else {
                res.put("status", 403);
                res.put("message", "您已经报名过了");
            }
        } else {
            res.put("status", 403);
            res.put("message", "用户识别失败！请检查信息");
        }

        return res;
    }
}
