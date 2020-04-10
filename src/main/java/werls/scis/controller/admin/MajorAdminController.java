package werls.scis.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisMajor;
import werls.scis.service.MajorServiceImpl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.admin
 * @Description: TODO
 * @date Date : 2020年04月10日 23:38
 */
@RestController
@RequestMapping("/admin/major")
public class MajorAdminController {
    @Autowired
    MajorServiceImpl service;


    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisMajor major) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status",200);
        res.put("data",service.save(major));
        return res;
    }


    @PostMapping("/repeat")
    public Map<String, Object> findByNameRepeat(@RequestParam("name") String name) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisMajor> majorOptional = service.findByMajorName(name);
        if (majorOptional.isPresent()) {
            res.put("data", "true");
        } else {
            res.put("data", "false");
        }
        return res;
    }
}
