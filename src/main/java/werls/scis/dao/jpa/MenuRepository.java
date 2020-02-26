package werls.scis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisMenu;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月26日 20:41
 */
public interface MenuRepository extends JpaRepository<ScisMenu,Integer> {

}
