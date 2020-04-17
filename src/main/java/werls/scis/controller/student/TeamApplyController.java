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

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody JSONObject jsonObject) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        userService.upUserTeam(
                jsonObject.getBoolean("isCaptain"),
                jsonObject.getBoolean("isApply"),
                jsonObject.getBoolean("isRead"),
                jsonObject.getInteger("teamId"),
                jsonObject.getInteger("userId"));
        return res;
    }


    @PostMapping("/find/My/Team/Captain/{id}")
    public Map<String, Object> findMyTeamIsCaptain(@PathVariable Integer id,
                                                   @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(name = "size", defaultValue = "20") Integer size,
                                                   @RequestParam(name = "teamName", defaultValue = "") String teamName,
                                                   @RequestParam(name = "competitionName", defaultValue = "") String competitionName,
                                                   @RequestParam(name = "isCaptain", defaultValue = "false") Boolean isCaptain) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<Map<String, Object>> tem = service.findMyTeamIsCaptain(id, competitionName, teamName, isCaptain, page, size);
//        List<Map<String, Object>> result = new ArrayList<>();
//        for (Map<String, Object> map : tem) {
//            Map<String, Object> temInfo = new HashMap<>(16);
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                temInfo.put(entry.getKey(), entry.getValue());
//            }
////            List<Map<String, Object>> teamMember = service.findTeamMember((Integer) map.get("teamId"));
////            List<Map<String, Object>> teamMemberRes = new ArrayList<>();
////            for (Map<String, Object> map1 : teamMember) {
////                Integer userId = (Integer) map1.get("userId");
////                Optional<ScisUser> userOptional = userService.findById(userId);
////                Map<String, Object> teamMemberInfo = new HashMap<>(16);
////                for (Map.Entry<String, Object> entry : map1.entrySet()) {
////                    teamMemberInfo.put(entry.getKey(), entry.getValue());
////                }
////                if (userOptional.isPresent()) {
////                    ScisUser user = userOptional.get();
////                    teamMemberInfo.put("ClassInfo", user.getScisClass());
////                }
////                teamMemberRes.add(teamMemberInfo);
////            }
////            temInfo.put("teamMember",teamMemberRes);
//            result.add(temInfo);
//        }

        res.put("status", 200);
        res.put("totalElements", tem.size());
        res.put("content", tem);
        return res;
    }

    @GetMapping("/find/Team/Member/{id}/{isApply}")
    public Map<String, Object> findTeamMember(@PathVariable Integer id, @PathVariable Boolean isApply) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<Map<String, Object>> tem = service.findTeamMember(id, isApply);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> map : tem) {
            Integer userId = (Integer) map.get("userId");
            Optional<ScisUser> userOptional = userService.findById(userId);
            Map<String, Object> temInfo = new HashMap<>(16);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                temInfo.put(entry.getKey(), entry.getValue());
            }
            if (userOptional.isPresent()) {
                ScisUser user = userOptional.get();
                temInfo.put("ClassInfo", user.getScisClass());
            }
//            userOptional.ifPresent(scisUser -> temInfo.put("userInfo", scisUser));
            result.add(temInfo);
        }
        res.put("status", 200);
        res.put("totalElements", result.size());
        res.put("content", result);
        return res;
    }

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


    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisTeamApply teamApply) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisUser user = teamApply.getScisUserList().get(0);
        Set<Integer> competitionId = new HashSet<>();
        Optional<ScisUser> userOptional = userService.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (user.getTeamApplies() != null) {
                for (ScisTeamApply apply : user.getTeamApplies()) {
                    if (apply.getCompetition() != null) {
                        competitionId.add(apply.getCompetition().getId());
                    }
                }
            }
            if (competitionId.add(teamApply.getCompetition().getId())) {
                ScisTeamApply result = service.save(teamApply);
                System.out.println(result.getId());
                userService.upUserTeam(true, true, true, result.getId(), user.getId());
                res.put("result", result);
                res.put("status", 200);
                res.put("message", "您的团队创建成功，快起邀请小伙伴加入吧！");
            } else {
                res.put("status", 403);
                res.put("message", "您已经有一个团队了");
            }
        } else {
            res.put("status", 403);
            res.put("message", "用户识别失败！请检查信息");
        }
        return res;
    }

    @PostMapping("/upData")
    public Map<String, Object> upData(@RequestBody ScisTeamApply teamApply) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        if (teamApply != null) {
            Optional<ScisTeamApply> teamApplyOptional = service.findById(teamApply.getId());
            if (teamApplyOptional.isPresent()) {
                ScisTeamApply tem = teamApplyOptional.get();
                ScisUser user = teamApply.getScisUserList().get(0);
                Set<Integer> competitionId = new HashSet<>();
                Optional<ScisUser> userOptional = userService.findById(user.getId());
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                    if (user.getTeamApplies() != null) {
                        for (ScisTeamApply apply : user.getTeamApplies()) {
                            if (apply.getCompetition() != null) {
                                competitionId.add(apply.getCompetition().getId());
                            }
                        }
                    }
                    if (competitionId.add(teamApply.getCompetition().getId())) {
                        List<ScisUser> userList = userService.findByTeamId(teamApply.getId());
                        /*控制成员人数*/
                        if (tem.getNumber() >= userList.size()) {
                            ScisTeamApply result = service.save(teamApply);
                            userService.upUserTeam(false, false, false, result.getId(), user.getId());
                            res.put("result", result);
                            res.put("status", 200);
                            res.put("message", "成功加入，可以去报名 -> 团队中查看自己的团队 ！");
                        } else {
                            res.put("status", 403);
                            res.put("message", "队伍人数已满！");
                        }
                    } else {
                        res.put("status", 403);
                        res.put("message", "您已经报名！");
                    }
                } else {
                    res.put("status", 403);
                    res.put("message", "用户识别失败！请检查信息");
                }

            }
        } else {
            res.put("status", 403);
            res.put("message", "错误的提交信息");
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
