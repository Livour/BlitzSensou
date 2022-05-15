import java.io.Serializable;

public class UserScore implements Serializable {
    public String username;
    public int score;

    UserScore(String username,int score){
        this.username=username;
        this.score=score;
    }
}
