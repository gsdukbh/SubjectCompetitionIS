package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 团体报名
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月21日 14:34
 */
@Entity
@Table(name = "Is_team_apply")
public class ScisTeamApply implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="team_id")
    private long id;
    @Column(name = "team_name")
    private String name;
    @Column(name = "team_number")
    private String number;
    @Column(name = "team_score")
    private String score;
    @Column(name = "team_apply_time")
    private Date applyTime;
    @Column(name = "team_rank")
    private String rank;
    @Column(name = "team_status")
    private String status;

    /**
     * 团队成员
     */
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name = "Is_team_user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")})
    private List<User>userList;

    /**
     * 竞赛（团队）
     */
     @ManyToOne(fetch = FetchType.LAZY,optional = false)
     @JoinColumn(name = "competition_id")
     private ScisCompetition competition;
}
