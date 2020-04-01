package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisProblem;
import werls.scis.dao.pojo.ScisReply;
import werls.scis.service.ReplyServiceImpl;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年03月30日 12:23
 */
@RestController
@RequestMapping("/tea")
public class ReplyController {
    @Autowired
    ReplyServiceImpl replyService;

    @PostMapping("/competition/problem/reply/save")
    public Map<String, Object> save(@RequestBody JSONObject json){
        Map<String, Object> res=new ConcurrentHashMap<>(10);
        if (json !=null){
            ScisReply reply=JSONObject.toJavaObject(json,ScisReply.class);
            replyService.save(reply);
            res.put("status", 200);
            res.put("message", "success");
            res.put("result",replyService.save(reply));
            return res;
        }else {
            res.put("status", 403);
            res.put("message", "fail");
            return res;
        }
    }
    @GetMapping("/competition/problem/reply/find/{id}")
    public Map<String, Object> find(@PathVariable Integer id){
        Map<String, Object> res=new ConcurrentHashMap<>(10);
        ScisReply reply=replyService.findByProblemId(id);
        if (reply !=null){
            res.put("status", 200);
            res.put("data",reply);
            return res;
        }else {
            res.put("status", 403);
            res.put("message", "查询数据为空");
            return res;
        }

    }
}
