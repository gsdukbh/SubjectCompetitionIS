package werls.scis.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    @Autowired
    UserService userService;

    @PostMapping("/findAll/{id}")
    public Map<String, Object> findAll(@PathVariable Integer id,
                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("applyTime"));
        Page<ScisApplyFrom> scisApplyFroms = serice.findAllByCompetitionId(id, pageable);
        List<ScisApplyFrom> scisApplyFromList = new ArrayList<>();
        for (ScisApplyFrom scisApplyFrom : scisApplyFroms.getContent()) {
            Integer userId = (Integer) serice.findUserIdByApplyId(scisApplyFrom.getId()).get("userId");
            Optional<ScisUser> optional = userService.findById(userId);
            optional.ifPresent(scisApplyFrom::setScisUser);
            scisApplyFromList.add(scisApplyFrom);
        }
        res.put("content", scisApplyFromList);
        res.put("totalElements", scisApplyFroms.getTotalElements());
        res.put("status", 200);


        return res;
    }

}
