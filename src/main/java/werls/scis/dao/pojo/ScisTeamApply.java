package werls.scis.dao.pojo;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    private Integer id;
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
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name = "Is_team_user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")})
    private List<ScisUser> scisUserList;

    /**
     * 竞赛（团队）
     */
     @ManyToOne(fetch = FetchType.EAGER,optional = false)
     @JoinColumn(name = "competition_id")
     private ScisCompetition competition;

    @Override
    public String toString() {
        return "ScisTeamApply{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", score='" + score + '\'' +
                ", applyTime=" + applyTime +
                ", rank='" + rank + '\'' +
                ", status='" + status + '\'' +
                ", userList=" + scisUserList +
                ", competition=" + competition +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ScisUser> getScisUserList() {
        return scisUserList;
    }

    public void setScisUserList(List<ScisUser> scisUserList) {
        this.scisUserList = scisUserList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public ScisCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(ScisCompetition competition) {
        this.competition = competition;
    }
}
