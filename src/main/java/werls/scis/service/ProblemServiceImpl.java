package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.ProblemRepository;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisProblem;

import java.sql.Date;
import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 23:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProblemServiceImpl {
    @Autowired
    private ProblemRepository problemRepository;

    /**
     * 指定时间查询 分页 排序
     * @param time  java.sql.Date;
     * @param pageable Pageable
     * @return Page<ScisProblem>
     */
   public Page<ScisProblem> findByTime(Date time , Pageable pageable){
       return problemRepository.findByTime(time, pageable);
   }

    /**
     * 问题类型 查询 分页 排序
     * @param type String 问题类型
     * @return Page<ScisProblem>
     */
    public Page<ScisProblem> findByType(String type , Pageable pageable){
        return problemRepository.findByType(type, pageable);
    }
    /**
     * 按照标题模糊查询 分页 排序
     * @param title String 标题
     * @param pageable Pageable
     * @return Page<ScisProblem>
     */
    public Page<ScisProblem> findByTitleLike(String title, Pageable pageable){
        return problemRepository.findByTitleLike(title, pageable);
    }

    public void save (ScisProblem problem){
        this.problemRepository.save(problem);
    }

    public void saveAll (List<ScisProblem> problem){
        this.problemRepository.saveAll(problem);
    }
    public Page<ScisProblem> findAll(Pageable pageable){
        return this.problemRepository.findAll(pageable);
    }


    public void delete(ScisProblem problem){
        this.problemRepository.delete(problem);
    }
    public void deleteAll(List<ScisProblem> problem){
        this.problemRepository.deleteAll(problem);
    }
    public void deleteById(Integer id){
        this.problemRepository.deleteById(id);
    }
    /**
     * 按照竞赛id进行查询
     * @param id id
     * @param pageable pageable
     * @return   Page<ScisProblem>
     */
    public Page<ScisProblem> findByCompetitionId(Integer id,Pageable pageable){
        return problemRepository.myFindCompetitionId(id,pageable);
    }
}
