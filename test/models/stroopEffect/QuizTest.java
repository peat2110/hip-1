package models.stroopEffect;

import models.ExperimentType;
import play.test.WithApplication;
import org.junit.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class QuizTest extends WithApplication{

    @Before
    public void setUp(){
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
    }

    @Test
    public void createQuiz(){
        Quiz q = new Quiz();
        assertNotNull(q);
    }

    @Test
    public void saveAndQuery(){
        new Quiz().save();
        assertNotNull(Quiz.find.byId(new Long(1)));
    }

    @Test
    public void create_and_connect_relation_success() {
        Trial t = new Trial();
        t.save();
        Question q = new Question();
        q.save();
        Quiz quiz = Quiz.create(t,q);
        quiz.save();
        assertEquals(t.trial_id,quiz.trial.trial_id);
        assertEquals(q.question_id,quiz.question.question_id);
    }
}
