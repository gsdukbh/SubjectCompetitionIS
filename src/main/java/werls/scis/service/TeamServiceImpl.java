package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.TeamApplyRepository;
import werls.scis.dao.pojo.ScisTeamApply;

import java.util.List;
import java.util.Optional;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月27日 23:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl {
    @Autowired
    private TeamApplyRepository teamApplyRepository;

    public Optional<ScisTeamApply> findById(Integer id) {
        return teamApplyRepository.findById(id);
    }

    public Page<ScisTeamApply> findAllByCompetitionId(Integer id, Pageable pageable) {
        return teamApplyRepository.findAllByCompetitionId(id, pageable);
    }

    public Page<ScisTeamApply> findByNameContainingAndCompetitionId(String name, Integer id, Pageable pageable) {
        return teamApplyRepository.findByNameContainingAndCompetitionId(name, id, pageable);
    }

    /**
     * 通过名称模糊查询，分页 排序
     *
     * @param nameLike String 团队名称
     * @return Page<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByNameLike(String nameLike, Pageable pageable) {
        return teamApplyRepository.findByNameLike(nameLike, pageable);
    }

    /**
     * 参赛状态 分页 排序
     *
     * @param status   String 状态
     * @param pageable Pageable
     * @return Page<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByStatus(String status, Pageable pageable) {
        return teamApplyRepository.findByStatus(status, pageable);
    }


    /**
     * 团队人数查询
     *
     * @param number Integer
     * @return age<ScisTeamApply>
     */
    public Page<ScisTeamApply> findByNumber(Integer number, Pageable pageable) {
        return teamApplyRepository.findByNumber(number, pageable);
    }

    public ScisTeamApply save(ScisTeamApply teamApply) {
        return teamApplyRepository.save(teamApply);
    }

    public void saveAll(List<ScisTeamApply> teamApply) {
        teamApplyRepository.saveAll(teamApply);
    }

    public Page<ScisTeamApply> findAll(Pageable pageable) {
        return teamApplyRepository.findAll(pageable);
    }

    public void delete(ScisTeamApply teamApply) {
        this.teamApplyRepository.delete(teamApply);
    }

    public void deleteAll(List<ScisTeamApply> teamApply) {
        this.teamApplyRepository.deleteAll(teamApply);
    }

    public void deleteById(Integer id) {
        this.teamApplyRepository.deleteById(id);
    }
}
