package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 竞赛
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:13
 */
@Entity
@Table(name = "Is_competition")
public class ScisCompetition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="competition_id")
    private long id;
    @Column(name = "competition_name")
    private String name;
    @Column(name = "competition_status")
    private String status;
    @Column(name = "competition_start_time")
    private Date startTime;
    @Column(name = "competition_end_time")
    private Date endTime;
    @Column(name = "competition_content")
    private String content;
    @Column(name = "competition_url")
    private String url;
    @Column(name = "competition_author")
    private String author;
    @Column(name = "competition_level")
    private String level;
    @Column(name = "competition_organizer")
    private String organizer;
    @Column(name = "competition_num_limit")
    private String numLimit;

    /**
     * 个人报名
     */
    @OneToMany(mappedBy = "competition",fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id",referencedColumnName = "competition_id")
    private List<ScisApplyFrom> applyFromList;
    /**
     * 团体报名
     */
    @OneToMany(mappedBy = "competition",fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id",referencedColumnName = "competition_id")
    private List<ScisTeamApply> teamApplyList;
    /**
     * 竞赛作品
     */
    @OneToMany(mappedBy = "competition")
    private ScisWorks works;
}
