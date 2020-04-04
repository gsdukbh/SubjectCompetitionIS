package werls.scis.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年04月04日 13:43
 */
@SpringBootTest
class CompetitionRepositoryTest {
    @Autowired
    CompetitionRepository competitionRepository;
    @Test
    void upStatus() {
        competitionRepository.upStatus();
    }

    @Test
    void upStatusA() {
        competitionRepository.upStatusA();
    }
}