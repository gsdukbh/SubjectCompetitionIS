package werls.scis.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
