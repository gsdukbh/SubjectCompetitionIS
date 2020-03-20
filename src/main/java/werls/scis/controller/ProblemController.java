package werls.scis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisProblem;
import werls.scis.service.ProblemServiceImpl;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月20日 17:09
 */
@RestController
@RequestMapping("/public/problem")
public class ProblemController {

    @Autowired
    ProblemServiceImpl service;

    public ScisProblem findByCompetitionId(){

        return null;
    }
}
