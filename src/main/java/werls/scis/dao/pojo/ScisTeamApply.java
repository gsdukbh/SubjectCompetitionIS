package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
@EntityListeners(AuditingEntityListener.class)
public class ScisTeamApply implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer id;
    @Column(name = "team_name")
    private String name;

    @Column(name = "team_number")
    private Integer number;


    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "team_apply_time")
    private Date applyTime;


    @Column(name = "team_status")
    private String status;
    @Column(name = "team_captain")
    private String captain;

    /**
     * 团队成员
     */
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties({"teamApplies"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Is_team_user",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<ScisUser> scisUserList;

    /**
     * 竞赛（团队）
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "competition_id")
    @JsonIgnoreProperties({"teamApplyList"})
    private ScisCompetition competition;

    @OneToOne()
    @JoinColumn(name = "works_id", referencedColumnName = "works_id")
    private ScisWorks works;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScisTeamApply)) {
            return false;
        }
        ScisTeamApply that = (ScisTeamApply) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(number, that.number) &&
                Objects.equals(applyTime, that.applyTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(captain, that.captain) &&
                Objects.equals(scisUserList, that.scisUserList) &&
                competition.equals(that.competition) &&
                Objects.equals(works, that.works);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, applyTime, status, captain, scisUserList, competition, works);
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
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

    //    @JsonIgnore
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
