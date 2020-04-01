package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.ReplyRepository;
import werls.scis.dao.pojo.ScisReply;

import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年03月22日 12:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReplyServiceImpl {
    @Autowired
    ReplyRepository replyRepository;

    /**
     * 保存
     * @param reply ScisReply 对象
     */
    public ScisReply save(ScisReply reply){
       return replyRepository.save(reply);
    }
    public Optional<ScisReply> findId(Integer id){
        return replyRepository.findById(id);
    }
    public  ScisReply findByProblemId(Integer id){
        return replyRepository.findByProblemId(id);
    }
}
