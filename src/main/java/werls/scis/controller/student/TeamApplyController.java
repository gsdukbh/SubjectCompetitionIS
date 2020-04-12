package werls.scis.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisTeamApply;
import werls.scis.service.TeamServiceImpl;

import java.util.Map;
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
@RequestMapping("/student")
public class TeamApplyController {
    @Autowired
    TeamServiceImpl service;

    @PostMapping("/findAll/{id}")
    public Map<String, Object> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size,
                                       @RequestParam(name = "name", defaultValue = "") String name,
                                       @PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("applyTime"));
        if (!"".equals(name)) {
            Page<ScisTeamApply> teamApplies = service.findByNameContainingAndCompetitionId(name, id, pageable);
            res.put("data", teamApplies.getContent());
            res.put("totalElements", teamApplies.getTotalElements());
            res.put("status", 200);
        } else {
            Page<ScisTeamApply> teamApplies = service.findAllByCompetitionId(id, pageable);
            res.put("data", teamApplies.getContent());
            res.put("totalElements", teamApplies.getTotalElements());
            res.put("status", 200);
        }
        return res;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisTeamApply teamApply) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        if (teamApply != null) {
            res.put("result", service.save(teamApply));
            res.put("status", 200);
        } else {
            res.put("status", 403);
        }
        return res;
    }
}
