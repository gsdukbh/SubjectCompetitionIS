package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 问题反馈
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:08
 */
@Entity
@Table(name = "Is_problem")
@EntityListeners(AuditingEntityListener.class)
public class ScisProblem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="problem_id")
    private Integer id;

    @Column(name = "problem_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date time;

    @Column(name = "problem_type")
    private String type;
    @Column(name = "problem_title")
    private String title;
    @Column(name = "problem_content", columnDefinition = "text")
    private String content;

    @Column(name = "problem_isReply")
    private Integer myReply;

    /**
     * 用户反馈问题
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private ScisUser scisUser;

    /**
     * 竞赛问题
     */

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"problems"})
    @JoinColumn(name = "competition_id")
//    @JsonIgnore
    private ScisCompetition competition;



    @OneToOne(cascade=CascadeType.ALL)
    @JsonIgnoreProperties({"problem"})
    private ScisReply reply;

    public Integer getMyReply() {
        return myReply;
    }

    public void setMyReply(Integer myReply) {
        this.myReply = myReply;
    }

    public ScisReply getReply() {
        return reply;
    }

    public void setReply(ScisReply reply) {
        this.reply = reply;
    }




    public ScisUser getScisUser() {
        return scisUser;
    }

    public void setScisUser(ScisUser scisUser) {
        this.scisUser = scisUser;
    }


    public ScisCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(ScisCompetition competition) {
        this.competition = competition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
