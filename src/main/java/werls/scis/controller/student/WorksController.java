package werls.scis.controller.student;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import werls.scis.dao.pojo.ScisWorks;
import werls.scis.service.WorksServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年04月17日 19:19
 */
@RestController
@RequestMapping("/student/works")
public class WorksController {
    @Autowired
    WorksServiceImpl worksService;

    @PostMapping("/findAll")
    public Map<String, Object> findAll() {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        Pageable pageable = PageRequest.of(0, 20);
        res.put("data", worksService.findAll(pageable));
        return res;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject works) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisWorks scisWorks = JSONObject.toJavaObject(works, ScisWorks.class);
        res.put("status", 200);
        res.put("res", works);
        res.put("sds", scisWorks);
//        res.put("result",worksService.save(works));
        return res;
    }
}
