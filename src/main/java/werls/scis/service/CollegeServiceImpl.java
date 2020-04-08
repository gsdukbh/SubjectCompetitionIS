package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.CollegeJpaRepository;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCollege;

import java.util.List;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 22:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CollegeServiceImpl {
    @Autowired
    private CollegeJpaRepository collegeJpaRepository;

    /**
     * 通过学院名称查询
     * @param collegeName 学院名称
     * @return 学院
     */
    @Cacheable(value = "college",unless = "#result == null ",key = "collegeName")
    public Optional<ScisCollege> findByCollegeName(String collegeName){
        return collegeJpaRepository.findByName(collegeName);
    }
    /**
     * 通过学院名称查询 模糊 分页 排序
     * @param collegeName 学院名称
     * @param pageable Pageable
     * @return Page<ScisCollege>
     */
    public Page<ScisCollege> findByCollegeNameLike(String collegeName, Pageable pageable){
        return collegeJpaRepository.findByNameLike(collegeName, pageable);
    }

    @CachePut(value = "college",unless = "#result == null ",key = "'college'")
    @CacheEvict(value = "college", key = "college.getName()")
    public ScisCollege save (ScisCollege college){
       return collegeJpaRepository.save(college);
    }
    @CachePut(value = "college",unless = "#result == null ",key = "'college'")
    public void saveAll (List<ScisCollege> college){
        collegeJpaRepository.saveAll(college);
    }

    @Cacheable(value = "college",unless = "#result == null ",key = "'college'")
    public Page<ScisCollege> findAll(Pageable pageable){
        return collegeJpaRepository.findAll(pageable);
    }
    public void delete(ScisCollege college){
        this.collegeJpaRepository.delete(college);
    }
    public void deleteAll(List<ScisCollege> college){
        this.collegeJpaRepository.deleteAll(college);
    }
    public void deleteById(Integer id){
        this.collegeJpaRepository.deleteById(id);
    }


}
