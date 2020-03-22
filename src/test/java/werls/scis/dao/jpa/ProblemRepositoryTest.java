package werls.scis.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    ProblemRepository repository;
    @Test
    void findByCompetitionId() {
        Pageable pageable1= PageRequest.of(0, 20);
        System.out.println(repository.findByCompetitionId(8,pageable1).getContent().get(1).getContent());
    }
}