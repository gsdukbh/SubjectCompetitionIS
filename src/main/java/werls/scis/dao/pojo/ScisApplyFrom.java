package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
@EntityListeners(AuditingEntityListener.class)
public class ScisApplyFrom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Integer id;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "apply_time")
    private Date applyTime;

    @Column(name = "apply_rank")
    private String gradesanking;

    @Column(name = "apply_score")
    private Integer score;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "apply_scoreUpTime")
    private Date scoreUpTime;

    @Column(name = "apply_status")
    private String status;

    @Column(name = "apply_prize")
    private String prize;
    /**
     * 竞赛用户
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"applyFroms"})
    private ScisUser scisUser;

    /**
     * 参加的竞赛
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "competition_id")
    @JsonIgnoreProperties({"applyFromList"})
    private ScisCompetition competition;

    @JsonIgnoreProperties({"applyFrom"})
    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "works_id", referencedColumnName = "works_id")
    private ScisWorks works;

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public Date getScoreUpTime() {
        return scoreUpTime;
    }

    public void setScoreUpTime(Date scoreUpTime) {
        this.scoreUpTime = scoreUpTime;
    }

    public String getGradesanking() {
        return gradesanking;
    }

    public void setGradesanking(String gradesanking) {
        this.gradesanking = gradesanking;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ScisWorks getWorks() {
        return works;
    }

    public void setWorks(ScisWorks works) {
        this.works = works;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public ScisUser getScisUser() {
        return scisUser;
    }

    public void setScisUser(ScisUser scisUser) {
        this.scisUser = scisUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }



    public ScisCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(ScisCompetition competition) {
        this.competition = competition;
    }
}
