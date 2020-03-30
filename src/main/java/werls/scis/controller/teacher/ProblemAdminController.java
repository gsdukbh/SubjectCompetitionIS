package werls.scis.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisProblem;
import werls.scis.service.ProblemServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年03月30日 21:48
 */
@RestController
@RequestMapping("/tea/problem")
public class ProblemAdminController {
    @Autowired
    ProblemServiceImpl service;

    @GetMapping("/find/competition/noReply/{id}")
    public Map<String, Object> noReply(@PathVariable Integer id){
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        Pageable pageable= PageRequest.of(1, 10, Sort.by("problem_time"));
        Page<ScisProblem> problems = service.findByReply(1,id,pageable);
        res.put("status",200);
        res.put("total",problems.getTotalElements());
        return res;
    }
}
