package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import werls.scis.dao.jpa.ReplyRepository;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年03月22日 12:09
 */
@Service
public class ReplyServiceImpl {
    @Autowired
    ReplyRepository replyRepository;

}
