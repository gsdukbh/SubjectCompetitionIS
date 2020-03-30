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
 * @date Date : 2020年03月30日 14:11
 */
@SpringBootTest

class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;
    @Test
    void findReplyNum() {

    }

    @Test
    void findByProblemId() {

        System.out.println(replyRepository.findByProblemId(1280));
    }
}