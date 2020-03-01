package werls.scis.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping("/login/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String session(){
        return "session 过期";
    }

    @Cacheable(value = "121",key = "3")
    @GetMapping("/admin")
    public String admin(){
        return "okokokokokokok";
    }



}
