package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年03月21日 19:09
 */
@Entity
@Table(name = "Is_reply")
@EntityListeners(AuditingEntityListener.class)
public class ScisReply implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Integer id;
//
//    @CreatedDate
//    @Column(name="reply_created_time")
//    private Date time;

    @CreatedDate
    @Column(name = "reply_reply_time")
    private Date replyTime;

    @Column(name = "reply_content")
    private String content;
    @Column(name = "reply_author")
    private String author;

    @Column(name = "problem_id")
    private Integer problemId;

    @JsonIgnore
    @OneToOne(mappedBy = "reply", cascade = CascadeType.ALL)
    @JoinColumn(name="problem_id",referencedColumnName = "problem_id")
//    @JsonIgnoreProperties({"reply"})
    private ScisProblem problem;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ScisProblem getProblem() {
        return problem;
    }

    public void setProblem(ScisProblem problem) {
        this.problem = problem;
    }
}
