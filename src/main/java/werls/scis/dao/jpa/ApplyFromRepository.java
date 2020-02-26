package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisApplyFrom;

import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 15:57
 */
public interface ApplyFromRepository extends JpaRepository<ScisApplyFrom,Integer> {

    List<ScisApplyFrom> findByStatus(String status);

    Page<ScisApplyFrom> findByStatus(String status, Pageable pageable);


}
