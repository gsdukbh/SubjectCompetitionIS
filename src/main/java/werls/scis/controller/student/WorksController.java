package werls.scis.controller.student;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.dao.pojo.ScisWorks;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.CompetitionServiceImpl;
import werls.scis.service.WorksServiceImpl;
import werls.scis.util.FileUploader;

import java.util.Map;
import java.util.Optional;
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
    @Autowired
    ApplyFromSericeImpl applyFromSerice;

    @Autowired
    CompetitionServiceImpl competitionService;

    @Autowired
    FileUploader fileUploader;

    @GetMapping("/findById/{id}")
    public Map<String, Object> findById(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisWorks> scisWorks = worksService.findById(id);
        res.put("status", 200);
        scisWorks.ifPresent(scisWork -> res.put("data", scisWork));
        return res;
    }

    @PostMapping("/findAll")
    public Map<String, Object> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size,
                                       @RequestParam(name = "name", defaultValue = "") String name) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        Pageable pageable = PageRequest.of(page, size, Sort.by("score").descending());

        res.put("data", worksService.findAll(pageable));
        return res;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject works) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisWorks scisWorks = JSONObject.toJavaObject(works, ScisWorks.class);
        ScisCompetition scisCompetition = new ScisCompetition();
        scisCompetition.setId(works.getInteger("competitionId"));
        scisWorks.setCompetition(scisCompetition);
        scisWorks = worksService.save(scisWorks);
        res.put("result", scisWorks);
        applyFromSerice.update(works.getInteger("userId"), scisCompetition.getId(), scisWorks.getId());
        res.put("status", 200);
        return res;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody JSONObject works) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisWorks scisWorks = JSONObject.toJavaObject(works, ScisWorks.class);
        Optional<ScisWorks> works1 = worksService.findById(scisWorks.getId());
//        works1.ifPresent(value -> fileUploader.remove(value.getBucketName(), value.getObjectName()));
        ScisCompetition scisCompetition = new ScisCompetition();
        if (works1.isPresent()) {
            scisCompetition = works1.get().getCompetition();
            if (!scisWorks.getObjectName().equals(works1.get().getObjectName())) {
                fileUploader.remove(works1.get().getBucketName(), works1.get().getObjectName());
            }
        }
        res.put("result", worksService.save(scisWorks));
        applyFromSerice.update(works.getInteger("userId"), scisCompetition.getId(), scisWorks.getId());
        res.put("status", 200);
        return res;
    }
}
