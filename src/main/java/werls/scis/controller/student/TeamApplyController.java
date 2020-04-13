package werls.scis.controller.student;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisTeamApply;
import werls.scis.dao.pojo.ScisTeamUserApply;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.TeamServiceImpl;
import werls.scis.service.UserService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年04月12日 23:02
 */
@RestController
@RequestMapping("/student/team")
public class TeamApplyController {
    @Autowired
    TeamServiceImpl service;
    @Autowired
    UserService userService;

    @Cacheable(value = "TeamApply", unless = "#result == null ", key = "#id")
    @PostMapping("/findAll/{id}")
    public Map<String, Object> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size,
                                       @RequestParam(name = "name", defaultValue = "") String name,
                                       @PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("applyTime"));

        if (!"".equals(name)) {

            Page<ScisTeamApply> teamApplies = service.findByNameContainingAndCompetitionId(name, id, pageable);
            this.addUserList(teamApplies, res);
        } else {

            Page<ScisTeamApply> teamApplies = service.findAllByCompetitionId(id, pageable);
            this.addUserList(teamApplies, res);
        }
        return res;
    }

    @CacheEvict(cacheNames = "TeamApply", key = "#teamApply.id")
    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisTeamApply teamApply) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisUser user = teamApply.getScisUserList().get(0);
        Optional<ScisUser> userOptional = userService.findById(user.getId());

        res.put("data", user);
//        if (teamApply != null) {
//            res.put("result", service.save(teamApply));
//            res.put("status", 200);
//        } else {
//            res.put("status", 403);
//        }
        return res;
    }

    @CacheEvict(cacheNames = "TeamApply", key = "#teamApply.id")
    @PostMapping("/upData")
    public Map<String, Object> upData(@RequestBody ScisTeamApply teamApply) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        if (teamApply != null) {
            Optional<ScisTeamApply> teamApplyOptional = service.findById(teamApply.getId());
            Integer numLim;
            if (teamApplyOptional.isPresent()) {
                ScisTeamApply tem = teamApplyOptional.get();
                List<ScisUser> userList = tem.getScisUserList();
                ScisUser user = new ScisUser();
                Set<ScisUser> userSet = new HashSet<>(userService.findByTeamId(tem.getId()));
                System.out.println(userSet.add(user));
                if (userSet.add(user)) {
                    userList.add(user);
                    tem.setScisUserList(userList);
                    numLim = tem.getNumber();
//                    if (numLim >= userList.size()) {
//                        res.put("result", service.save(tem));
//                        res.put("status", 200);
//                    } else {
//                        res.put("message", "队伍人数已满！");
//                        res.put("status", 403);
//                    }
                } else {
                    res.put("status", 403);
                    res.put("message", "您已经报名！");
                }

            }
        } else {
            res.put("status", 403);
        }
        return res;
    }

    @GetMapping("/find/member/{team}")
    public Map<String, Object> team(@PathVariable Integer team) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<ScisUser> teamApplyList = userService.findByTeamId(team);
        List<ScisTeamUserApply> teamUserApplies = new ArrayList<>();
        for (ScisUser user1 : teamApplyList) {
            Map<String, Object> tem = userService.tex(team, user1.getId());
            ScisTeamUserApply apply = new ScisTeamUserApply();
            apply.setApply((Boolean) tem.get("isApply"));
            apply.setCaptain((Boolean) tem.get("isCaptain"));
            apply.setRead((Boolean) tem.get("isRead"));
            apply.setUser(user1);
            teamUserApplies.add(apply);
        }
        res.put("status", 200);
        res.put("data", teamUserApplies);
        return res;
    }

    private void addUserList(Page<ScisTeamApply> teamApplies, Map<String, Object> res) {
        List<ScisTeamApply> teamApplyList = new ArrayList<>();
        for (ScisTeamApply scisTeamApply : teamApplies.getContent()) {
            scisTeamApply.setScisUserList(userService.findByTeamId(scisTeamApply.getId()));
            teamApplyList.add(scisTeamApply);
        }
        res.put("data", teamApplyList);
        res.put("totalElements", teamApplies.getTotalElements());
        res.put("status", 200);
    }
}
