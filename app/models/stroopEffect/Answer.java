package models.stroopEffect;

import models.User;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table (name="stroop_answer")
public class Answer extends Model{

    @Id
    public long answer_id;
    @Column(length=20)
    public String answer;
    public double used_time;

    @ManyToOne
    public User user;
    @ManyToOne
    public Quiz quiz;

    public Answer(String answer,double usedTime){
        this.answer = answer;
        this.used_time = usedTime;
    }

    public static Answer create(String answer, double usedTime,User user, Quiz quiz){
        Answer newAnswer = new Answer(answer,usedTime);
        newAnswer.user = user;
        newAnswer.quiz = quiz;
        return newAnswer;
    }

    public static List<Answer> findInvolving(User user, List<Quiz> quizzes){
        List<Answer> answers = new ArrayList<Answer>();
        for(Quiz quiz:quizzes){
            answers.add(find.where().eq("user" ,user).eq("quiz",quiz).findUnique());
        }
        return answers;
    }

    public static double calculateTotalUsedTime(List<Answer> answers) {
        double totalUsedTime = 0.0;
        for(Answer ans : answers) {
            totalUsedTime += ans.used_time;
        }
        return totalUsedTime;
    }

    public static int calculateTotalScore(List<Answer> answers) {
        int totalScore = 0;

        for(Answer ans : answers) {
            if(ans.answer.equals(ans.quiz.question.ink_color)) {
                totalScore++;
            }
        }
        return totalScore;
    }

    @SuppressWarnings("unchecked")
    public static Finder<Long,Answer> find = new Finder(Long.class,Answer.class);

}
