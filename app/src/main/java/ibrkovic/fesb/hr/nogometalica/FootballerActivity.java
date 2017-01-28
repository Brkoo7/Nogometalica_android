package ibrkovic.fesb.hr.nogometalica;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class FootballerActivity extends Activity {

    private String score;
    private TextView timer;
    private TextView points;
    private TextView totalScore;
    private ImageView footballer;
    private Button btn0, btn1, btn2, btn3;
    private TextView resultMessage;
    private Button correctButton;
    private Boolean isFinished;
    private int correctPosition;
    private int difficulty;
    private  String username;
    private Button restart;
    int correctPoints, usePoints;
    int sumScore;
    int time;
    int tocan;

    /** Svi nogometasi u bazi */
    ArrayList<Nogometas> nogometasi = new ArrayList<Nogometas>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footballer);



        /** Inicijalne postavke */
        isFinished = false;
        timer = (TextView) findViewById(R.id.timerId);
        points = (TextView) findViewById(R.id.pointsId);
        totalScore = (TextView) findViewById(R.id.totalScore);
        footballer = (ImageView) findViewById(R.id.footballerPictureId);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        restart = (Button) findViewById(R.id.restartId);
        resultMessage = (TextView) findViewById(R.id.result);

        /** Dohvacanje imena i tezine igre iz glavnog activity-a */
        Bundle extras = getIntent().getExtras();
        difficulty = extras.getInt("level");
        username = extras.getString("name");

        NogometasDataSource nogometasDataSource = new NogometasDataSource(getApplicationContext());

        try {
            nogometasDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /** Svi nogometasi iz baze */
        nogometasi = nogometasDataSource.getAllNogometasi();

        /** Generiraj pitanje */
        generateQuestion();
        /** Pozovi timer */
        startTimer();
    }

    public void startTimer() {
        /** Ovisno o tezini igre postavi vremensko ogranicenje igre*/
        switch (difficulty) {
            case 0:
                time = 31000;
                break;
            case 1:
                time = 21000;
                break;
            case 2:
                time = 11000;
                break;
        }

        /** Objekt koji je u memoriji sve dok ne istekne vrijeme, parametri u ms*/
        new CountDownTimer(time, 1000) {
            /**
             * Koliko je sekundi preostalo do isteka vremena
             * @param millisUntilFinished
             */
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished/1000 + "s");
            }

            /** Završetak igre */
            public void onFinish() {
                timer.setText("0s");
                resultMessage.setText("Result: " + Integer.toString(correctPoints) + "/" + Integer.toString(usePoints));
                score =  Integer.toString(correctPoints) + "/" + Integer.toString(usePoints);

                /** Uspostavi koneckiju prema User tablici u bazi */
                UserDataSource userDataSource = new UserDataSource(getApplicationContext());

                try {
                    userDataSource.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                /**Ukupan rezultat na kraju igre u bodovima */
                sumScore = correctPoints * 10;
                totalScore.setText(Integer.toString(sumScore));

                userDataSource.addUserToDb(username, String.valueOf(sumScore));

                /** Igra završila*/
                isFinished = true;
                restart.setVisibility(VISIBLE);
            }
        }.start();
    }

    public void generateQuestion() {

        Random rand = new Random();
        boolean postoji = false;



        /**polje koje se koristi za privremenu pohranu 4 nogometasa - odgovora od kojih je jedan točan*/
        ArrayList<Nogometas> nogometasiPonudjeni = new ArrayList<Nogometas>();
        for (Nogometas nogometas: nogometasiPonudjeni) {
            Log.d("Pogadanje", nogometas.getName());
        }

        for(int i=1;i<4;)
        {
            postoji = false;
            int pos = rand.nextInt(nogometasi.size());
            Nogometas nogometas = (Nogometas) nogometasi.get(pos);
            if(nogometasiPonudjeni.size() == 0)
            {
                nogometasiPonudjeni.add(nogometas);
            }
            else
            {
                for (Nogometas nogometas1:nogometasiPonudjeni) {
                    if(nogometas.getName() == nogometas1.getName()){
                        postoji = true;
                    }
                };
                if(!postoji) {
                    nogometasiPonudjeni.add(nogometas);
                    i++;
                    postoji = false;
                }
            }
        }

        tocan = rand.nextInt(4);

        String mDrawableName = nogometasiPonudjeni.get(tocan).getUrl();
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        Log.d("ID", String.valueOf(resID));
        footballer.setImageResource(resID);

        btn0.setText(nogometasiPonudjeni.get(0).getName());
        btn1.setText(nogometasiPonudjeni.get(1).getName());
        btn2.setText(nogometasiPonudjeni.get(2).getName());
        btn3.setText(nogometasiPonudjeni.get(3).getName());
    }

    /**
     * Vraća poziciju kliknutog elementa
     *
     * Funcija se aktivira prilikom klika na neki od botuna za odgovor na pitanje te prvo provjeri da li je odgovor
     * ispravan, te spremi rezultat. Odmah nakon toga generira novo pitanje
     * @param view
     */
    public void getPosition(View view) {
        if(isFinished == false) {
            /**
             * Provjeri da li kliknuti botun odgovara točnom botunu
             */
            if(view.getTag().toString().equals(Integer.toString(tocan))) {
                correctPoints++;
            }

            usePoints++;
            points.setText(Integer.toString(correctPoints) + "/" + Integer.toString(usePoints));
            generateQuestion();
        }
    }

    public void restartGame(View view) {
        resultMessage.setText("");
        totalScore.setText("");
        restart.setVisibility(INVISIBLE);
        isFinished=false;
        correctPoints = 0;
        usePoints = 0;
        startTimer();
        points.setText(Integer.toString(correctPoints) + "/" + Integer.toString(usePoints));
        resultMessage.setText("");
        generateQuestion();
    }
}





