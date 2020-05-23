package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.MajorRepostitory;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisMajor;

import java.util.List;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 23:12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MajorServiceImpl {
    @Autowired
    private MajorRepostitory major;

    public void myDeleteById(Integer id) {
        major.myDeleteById(id);
    }

    public void myDeleteByIdAll(List<ScisMajor> majors) {
        for (ScisMajor major1 : majors) {
            major.myDeleteById(major1.getId());
        }

    }

    public Page<ScisMajor> findByNameContaining(String majorName, Pageable pageable) {
        return major.findByNameContaining(majorName, pageable);
    }

    public List<ScisMajor> findByAll() {
        return major.findAll();
    }

    public Page<ScisMajor> findByAll(Pageable pageable) {
        return major.findAll(pageable);
    }

    /**
     * @param majorLevel String 培养层次
     * @return Page<ScisMajor>
     */
    public Page<ScisMajor> findByMajorLevel(String majorLevel, Pageable pageable) {
        return major.findByLevel(majorLevel, pageable);
    }

    /***
     * 按照专业名字查询
     * @param majorName 专业名字
     * @return ScisMajor
     */
    public Optional<ScisMajor> findByMajorName(String majorName) {
        return major.findByName(majorName);
    }

    public ScisMajor save(ScisMajor major) {
        return this.major.save(major);
    }

    public void saveAll(List<ScisMajor> major) {
        this.major.saveAll(major);
    }

    public Page<ScisMajor> findAll(Pageable pageable) {
        return this.major.findAll(pageable);
    }


    public void delete(ScisMajor major) {
        this.major.delete(major);
    }

    public void deleteAll(List<ScisMajor> major) {
        this.major.deleteAll(major);
    }

    public void deleteById(Integer id) {
        this.major.deleteById(id);
    }

}
