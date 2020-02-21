package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * 个人竞赛报名
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:01
 */
@Entity
@Table(name = "Is_apply_from")
public class ScisApplyFrom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="apply_id")
    private long id;
    @Column(name = "apply_time")
    private Date applyTime;
    @Column(name = "apply_rank")
    private String rank;
    @Column(name = "apply_status")
    private String status;

    /**
     * 竞赛用户
     */
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    /**
     * 参加的竞赛
     */
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "competition_id")
    private ScisCompetition competition;



}
