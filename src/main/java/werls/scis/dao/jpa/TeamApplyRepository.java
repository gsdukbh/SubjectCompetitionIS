package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisTeamApply;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 23:29
 */
public interface TeamApplyRepository extends JpaRepository<ScisTeamApply,Integer> {
    /**
     * 通过名称模糊查询，分页 排序
     * @param nameLike String 团队名称
     * @return Page<ScisTeamApply>
     */
    Page<ScisTeamApply> findByNameLike(String nameLike);
}
