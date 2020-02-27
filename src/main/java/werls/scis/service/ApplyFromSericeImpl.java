package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import werls.scis.dao.jpa.ApplyFromRepository;
import werls.scis.dao.pojo.ScisApplyFrom;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 15:56
 */
@Service
public class ApplyFromSericeImpl {
    @Autowired
    private  ApplyFromRepository apply;
    /**
     * 报名状态 分页 排序
     * @param status 状态
     * @param pageable Pageable
     * @return List<ScisApplyFrom>
     */
    public Page<ScisApplyFrom> findByStatus(String status, Pageable pageable){
        return apply.findByStatus(status, pageable);
    }
}
