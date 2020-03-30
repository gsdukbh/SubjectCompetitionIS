package werls.scis.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import werls.scis.service.ProblemServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年03月21日 15:13
 */
@SpringBootTest
class ProblemRepositoryTest {
    @Autowired
    ProblemServiceImpl repository;
    Pageable pageable1= PageRequest.of(0, 20,Sort.by("problem_time"));
    @Test
    void findByCompetitionId() {
        System.out.println(repository.findByCompetitionId(8,pageable1).getContent().get(1).getReply());
    }


    @Test
    void myFindCompetitionId() {
        System.out.println(repository.findByCompetitionId(8,pageable1));
    }

    @Test
    void myFindByReply() {
        System.out.println(repository.findByReply(8,8,pageable1));
    }
}