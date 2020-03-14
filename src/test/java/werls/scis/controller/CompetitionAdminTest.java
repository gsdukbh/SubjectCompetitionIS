package werls.scis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import werls.scis.service.CompetitionServiceImpl;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月14日 10:23
 */
@SpringBootTest
class CompetitionAdminTest {

    @Resource
    CompetitionServiceImpl competitionService;

    @Test
    void findByAll() {


    }
}