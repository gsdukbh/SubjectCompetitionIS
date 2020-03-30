package werls.scis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import werls.scis.dao.pojo.ScisProblem;
import werls.scis.dao.pojo.ScisReply;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年03月22日 12:05
 */
public interface ReplyRepository extends JpaRepository<ScisReply, Integer> {

    /**
     * 查找问题回复
     * @param id Integer
     * @return ScisReply
     */
    ScisReply findByProblemId(Integer id);
}
