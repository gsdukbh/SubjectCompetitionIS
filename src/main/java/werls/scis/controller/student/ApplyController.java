package werls.scis.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.service.ApplyFromSericeImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年04月13日 0:21
 */
@RestController
@RequestMapping("/student/apply")
public class ApplyController {

    @Autowired
    ApplyFromSericeImpl serice;

    @PostMapping("/findAll/{id}")
    public Map<String, Object> findAll(@PathVariable Integer id,
                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("applyTime"));
        res.put("status", 200);
        res.put("data", serice.findAllByCompetitionId(id, pageable));
        return res;
    }
}
