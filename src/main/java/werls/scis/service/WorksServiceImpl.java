package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.WorksRepository;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.dao.pojo.ScisWorks;

import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 23:45
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorksServiceImpl {
    @Autowired
    private WorksRepository worksRepository;

    public void delete(ScisWorks works) {
        this.worksRepository.delete(works);
    }

    public void deleteAll(List<ScisWorks> works) {
        this.worksRepository.deleteAll(works);
    }

    public void deleteById(Integer id) {
        this.worksRepository.deleteById(id);
    }

    public ScisWorks save(ScisWorks works) {
        return worksRepository.save(works);
    }

    public void save(List<ScisWorks> works) {
        worksRepository.saveAll(works);
    }

    public Page<ScisWorks> findAll(Pageable pageable) {
        return worksRepository.findAll(pageable);
    }


    /**
     * 模糊查询 作品名
     * @param nameLike String
     * @param pageable Pageable
     * @return   Page<ScisWorks>
     */
    public Page<ScisWorks> findByNameLike(String nameLike, Pageable pageable){
        return worksRepository.findByNameContaining(nameLike, pageable);
    }
    /**
     * author By Like sort page
     * @param authorLike String
     * @param pageable Pageable
     * @return  Page<ScisWorks>
     */
    public Page<ScisWorks> findByAuthorLike(String authorLike,Pageable pageable){
        return worksRepository.findByAuthorContaining(authorLike, pageable);
    }


    /**
     * 作品分数
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    public Page<ScisWorks> findByScore(Integer score,Pageable pageable){
        return worksRepository.findByScore(score, pageable);
    }
    /**
     *  > score
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    public Page<ScisWorks>findByScoreAfter(Integer score, Pageable pageable){
        return worksRepository.findByScoreAfter(score, pageable);
    }
    /**
     * < score
     * @param score Integer
     * @param pageable Pageable
     * @return    Page<ScisWorks>
     */
    public Page<ScisWorks> findByScoreBefore(Integer score , Pageable pageable){
        return worksRepository.findByScoreBefore(score, pageable);
    }
    /**
     *  start <= score <=end
     * @param start Integer
     * @param end Integer
     * @param pageable  Pageable
     * @return Page<ScisWorks>
     */
    public Page<ScisWorks> findByScoreBetween(Integer start ,Integer end , Pageable pageable){
        return worksRepository.findByScoreBetween(start, end, pageable);
    }
    /**
     * author and ? > score
     * @param author String
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    public Page<ScisWorks> findByAuthorAndScoreAfter(String author,Integer score,Pageable pageable){
        return worksRepository.findByAuthorAndScoreAfter(author, score, pageable);
    }
    /**
     * author and ? < score
     * @param author String
     * @param score Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    public Page<ScisWorks> findByAuthorAndScoreBefore(String author,Integer score,Pageable pageable){
        return worksRepository.findByAuthorAndScoreBefore(author, score, pageable);
    }
    /**
     * author and start <= score <= end
     * @param author  String
     * @param start Integer
     * @param end Integer
     * @param pageable Pageable
     * @return Page<ScisWorks>
     */
    public Page<ScisWorks> findByAuthorAndScoreBetween(String author,Integer start,Integer end,Pageable pageable){
        return worksRepository.findByAuthorAndScoreBetween(author, start, end, pageable);
    }

    /**
     * 查询竞赛作品 需要 ScisCompetition ID
     * @param competition ScisCompetition
     * @param pageable Pageable
     * @return  Page<ScisWorks>
     */
    public Page<ScisWorks> findByCompetition(ScisCompetition competition, Pageable pageable){
        return worksRepository.findByCompetition(competition, pageable);
    }

}
