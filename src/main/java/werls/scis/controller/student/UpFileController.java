package werls.scis.controller.student;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年03月23日 22:09
 */
@RestController
@RequestMapping("/i/upFile")
public class UpFileController {

    @PostMapping("/img")
    public Map<String,Object> saveImg(@RequestParam("file") MultipartFile file){
        Map<String, Object> res=new ConcurrentHashMap<>(5);

        return null;
    }
}
