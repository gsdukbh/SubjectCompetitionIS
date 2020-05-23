package werls.scis.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisMajor;
import werls.scis.service.MajorServiceImpl;

import java.util.HashMap;
import java.util.List;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MajorServiceImpl service;

    @PostMapping("/find")
    public Map<String, Object> find(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", defaultValue = "20") Integer size,
                                    @RequestParam(name = "name", defaultValue = "") String name) {
        Map<String, Object> res = new HashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        if (!"".equals(name)) {
            Page<ScisMajor> majors = service.findByNameContaining(name, pageable);
            res.put("content", majors.getContent());
            res.put("totalElements", majors.getTotalElements());
        } else {
            Page<ScisMajor> majorList = service.findByAll(pageable);
            res.put("content", majorList.getContent());
            res.put("totalElements", majorList.getTotalElements());
        }
        res.put("status", 200);
        return res;
    }


    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisMajor major) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("data", service.save(major));
        return res;
    }

    @PostMapping("/del")
    public Map<String, Object> del(@RequestBody ScisMajor major) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        try {
            logger.error(major.getId().toString());
//            service.deleteById(major.getId());
            service.myDeleteById(major.getId());
            res.put("status", 200);
        } catch (Exception e) {
            res.put("status", 403);
            res.put("message", "该专业还有其他班级信息，拒绝删除");
            logger.warn("{}专业还有其他班级信息,异常信息：{}", major.getName(), e.toString());
        }
        return res;
    }

    @PostMapping("/delAll")
    public Map<String, Object> delAll(@RequestBody List<ScisMajor> majorList) {
        Map<String, Object> res = new HashMap<>(16);
        try {
//            service.deleteAll(majorList);
            service.myDeleteByIdAll(majorList);
            res.put("status", 200);
        } catch (Exception e) {
            res.put("status", 403);
            res.put("message", "该专业还有其他班级信息，拒绝删除");
            logger.warn("专业还有其他班级信息,异常信息：{}", e.toString());
        }
        return res;
    }

    @PostMapping("/repeat")
    public Map<String, Object> findByNameRepeat(@RequestParam("name") String name) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisMajor> majorOptional = service.findByMajorName(name);
        if (majorOptional.isPresent()) {
            res.put("MID", majorOptional.get().getId());
            res.put("data", "true");
        } else {
            res.put("data", "false");
        }
        return res;
    }
}
