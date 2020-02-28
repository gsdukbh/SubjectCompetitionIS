package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.MajorRepostitory;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisMajor;

import java.util.List;

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
    /**
     *
     * @param majorLevel String 培养层次
     * @return Page<ScisMajor>
     */
   public Page<ScisMajor> findByMajorLevel(String majorLevel, Pageable pageable){
       return major.findByMajorLevel(majorLevel, pageable);
   }
    /***
     * 按照专业名字查询
     * @param majorName 专业名字
     * @return ScisMajor
     */
    public ScisMajor findByMajorName(String majorName){
        return major.findByMajorName(majorName);
    }
    public void save(ScisMajor major){
        this.major.save(major);
    }
    public void saveAll(List<ScisMajor> major){
        this.major.saveAll(major);
    }
    public Page<ScisMajor> findAll( Pageable pageable){
        return this.major.findAll(pageable);
    }


    public void delete(ScisMajor major){
        this.major.delete(major);
    }
    public void deleteAll(List<ScisMajor> major){
        this.major.deleteAll(major);
    }
    public void deleteById(Integer id){
        this.major.deleteById(id);
    }

}
