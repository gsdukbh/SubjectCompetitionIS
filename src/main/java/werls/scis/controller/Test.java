package werls.scis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年02月22日 18:51
 */
@RestController
public class Test {

    @RequestMapping("/admin")
    public String in(){
        return "ok";
    }

    @RequestMapping("/login/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String session(){
        return "session 过期";
    }
}
