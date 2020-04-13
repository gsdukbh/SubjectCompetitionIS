package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.ApplyFromRepository;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.dao.pojo.ScisApplyFrom;

import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 15:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApplyFromSericeImpl {
    @Autowired
    private ApplyFromRepository apply;


    public Page<ScisApplyFrom> findAllByCompetitionId(Integer id, Pageable pageable) {
        return apply.findAllByCompetitionId(id, pageable);
    }


    /**
     * 报名状态 分页 排序
     *
     * @param status   状态
     * @param pageable Pageable
     * @return List<ScisApplyFrom>
     */

    public Page<ScisApplyFrom> findByStatus(String status, Pageable pageable) {
        return apply.findByStatus(status, pageable);
    }


    public ScisApplyFrom save(ScisApplyFrom applyFrom) {
        return apply.save(applyFrom);
    }

    public List<ScisApplyFrom> save(List<ScisApplyFrom> applyFrom) {
        return apply.saveAll(applyFrom);
    }

    public Page<ScisApplyFrom> findAll(Pageable pageable) {
        return apply.findAll(pageable);
    }

    public void delete(ScisApplyFrom applyFrom) {
        this.apply.delete(applyFrom);
    }

    public void deleteAll(List<ScisApplyFrom> applyFrom) {
        this.apply.deleteAll(applyFrom);
    }

    public void deleteById(Integer id) {
        this.apply.deleteById(id);
    }

}
