package meshari.uoregon.piggamev1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rLayout;
    static EditText player1Name;
    static EditText player2Name;
    TextView player1Score;
    TextView player2Score;
    static TextView turn;
    TextView turnLablel;
    ImageView  DieImage;
    TextView points;
    static public boolean playerOneturn = true;
    static public boolean playerTwoturn = false;
    Players plyrOne = Players.PlayerOne;
    Players plyrTwo = Players.PlayerTwo;
    Piggame game = new Piggame();
    MediaPlayer backMusic;
    MediaPlayer clickSound;
    MediaPlayer booSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rLayout = (RelativeLayout) findViewById(R.id.Rlayout);
        player1Name = (EditText)findViewById(R.id.player1Name);
        player2Name = (EditText)findViewById(R.id.player2Name);
        player1Score = (TextView)findViewById(R.id.player1Score);
        player2Score = (TextView)findViewById(R.id.player2Score);
        turnLablel = (TextView)findViewById(R.id.turnLablel);
        turn = (TextView)findViewById(R.id.turn);
        DieImage = (ImageView)findViewById(R.id.imageViewDie);
        points = (TextView)findViewById(R.id.points);
        backMusic = MediaPlayer.create(MainActivity.this, R.raw.background);
        clickSound = MediaPlayer.create(MainActivity.this, R.raw.buttonclick);
        booSound = MediaPlayer.create(MainActivity.this, R.raw.boo);
        backMusic.setLooping(true);
        backMusic.start();
        onTouchMethod();
    }

    @Override
    protected void onPause(){

        super.onPause();
        backMusic.release();
        finish();
    }

    protected void clickRolldie(View v){

        clickSound.start();

        if(playerOneturn){
            turn.setText(player1Name.getText());
            game.getImage(game.rolls());
            game.play(plyrOne);
            DieImage.setImageResource(game.getId());
            points.setText(Integer.toString(game.getCounter1()));

        }
        else{
            turn.setText(player2Name.getText());
            game.getImage(game.rolls());
            game.play(plyrTwo);
            DieImage.setImageResource(game.getId());
            points.setText(Integer.toString(game.getCounter2()));
        }

        if(game.getPoint() == 0){
            booSound.start();
        }
    }

    protected void clickEndturn(View v){

        clickSound.start();
        points.setText("0");

        if (playerOneturn) {
            game.setSum1(game.getSum1() + game.getCounter1());
            player1Score.setText(Integer.toString(game.getSum1()));
            game.setCounter1(0);
            playerOneturn = false;
            playerTwoturn = true;
            turn.setText(player2Name.getText());
        } else if (playerTwoturn) {
            game.setSum2(game.getSum2() + game.getCounter2());
            player2Score.setText(Integer.toString(game.getSum2()));
            game.setCounter2(0);
            playerTwoturn = false;
            playerOneturn = true;
            turn.setText(player1Name.getText());
        }
    }

    protected void clickNewgame(View v){

        clickSound.start();

        player1Name.setText("");
        player2Name.setText("");
        player1Score.setText("0");
        player2Score.setText("0");
        turn.setText("");
        points.setText("0");
        playerOneturn = true;
        playerTwoturn = false;
        if(game.getSum2() > game.getSum2()){
            System.out.println("player one wins");
        }
        else if (game.getSum1() == game.getSum2()){
            System.out.println("Tie");
        }else {System.out.println("player two wins");}
        game.resetGame();
    }


    /*onTouchMethod: hide the soft keyboard when you click outside of editbox on screen*/
    public void onTouchMethod(){
        rLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });
    }

    /*Set the turn text to Playertwo's turn if Player one got die1 which is = 0 and vice versa*/
    public static void setText() {
        if (playerOneturn){
            turn.setText(player1Name.getText());
        }else{turn.setText(player2Name.getText());}
    }

}