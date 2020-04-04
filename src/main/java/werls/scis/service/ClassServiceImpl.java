package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.ClassJpaRepository;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisClass;

import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 19:17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ClassServiceImpl {

    @Autowired
    ClassJpaRepository classJpaRepository;

    public List<ScisClass> findAll(){
        return classJpaRepository.findAll();
    }

  /**
     * 使用班级名称查询，全称查询
     * @param name 班级名称
     * @return 班级
     */

    public ScisClass findByName(String name){
        return classJpaRepository.findByName(name);
    }

    /**
     * 分页查询，可在pageable 加入 Sort
     * @param name 班级名称
     * @param pageable pageable
     * @return Page<ScisClass>
     */

    public Page<ScisClass> findByName(String name, Pageable pageable){
        return classJpaRepository.findByName(name, pageable);
    }

    /**
     * 模糊查询，分页查询，可在pageable 加入 Sort
     * @param name 班级名称
     * @param pageable pageable
     * @return Page<ScisClass>
     */

    public Page<ScisClass> findByNameLike(String name,Pageable pageable){
        return classJpaRepository.findByNameLike(name, pageable);
    }

    public ScisClass save(ScisClass scisClass){
        return classJpaRepository.save(scisClass);
    }
    public List<ScisClass> save( List<ScisClass> scisClass){
        return classJpaRepository.saveAll(scisClass);
    }

    public Page<ScisClass> findAll( Pageable pageable){
        return classJpaRepository.findAll(pageable);
    }
    public void delete(ScisClass scisClass){
        this.classJpaRepository.delete(scisClass);
    }
    public void deleteAll(List<ScisClass>  scisClass){
        this.classJpaRepository.deleteAll(scisClass);
    }
    public void deleteById(Integer id){
        this.classJpaRepository.deleteById(id);
    }
}
