package ibrkovic.fesb.hr.nogometalica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private EditText name;
    private SeekBar setLevel;
    private TextView yourLevel;
    private TextView validation;
    private Button footballer;
    private Button seeScore;
    private Button dbase;
    private int difficulty;
    private String playerName;
    private int playerAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        footballer = (Button) findViewById(R.id.footballerId);
        dbase = (Button) findViewById(R.id.bazaId);
        setLevel = (SeekBar) findViewById(R.id.customLevelId);
        yourLevel = (TextView) findViewById(R.id.chooseLevelId);
        validation = (TextView) findViewById(R.id.validationId);
        seeScore = (Button) findViewById(R.id.scoreId);
        footballer.setEnabled(false);


        /**
         * Uspostavljanje konekcije prema bazi nogometasa
         */
        NogometasDataSource nogometasDataSource = new NogometasDataSource(getApplicationContext());

        try {
            nogometasDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /** Provjera da li je baza nogometasa prazna
         *
         * Ukoliko je baza prazna napuni je s igracima, to se radi samo jednom prilikom
         * instaliranja aplikacije. Ukoliko je baza vec napunjena ne radi nista
         */
        int pos = nogometasDataSource.getDatabaseCount();

        if(pos == 0)
        {
            /**
             * Napuni stringove te ih nakon toga šalji u bazu
             *
             * urlovi[] predstavljaju imena fajlova u drawable folderu
             */
            String[] imena = {"Messi","Ronaldo","Drogba", "Modric", "Sallah", "Dybala","Recoba", "Stam", "Essien", "Zaneti",
                                "Olic", "Rakitic", "Pirlo", "Nagatomo", "Iniesta", "Byram", "Dawson", "Diomande", "Paulista",
                                "Antonio", "Arbeloa", "Asoro", "Behrami", "Boruc","Clasie", "Davis", "Dias", "Subasic",
                                "Simunic", "Hulk"};
            String[] urlovi = {"messi","ronaldo","drogba", "modric", "sallah", "dybala","recoba", "stam", "essien", "zaneti",
                                "olic", "rakitic", "pirlo", "nagatomo", "iniesta", "byram", "dawson", "diomande", "paulista",
                                "antonio", "arbeloa", "asoro", "behrami", "boruc","clasie", "davis", "dias", "subasic",
                                "simunic", "hulk"};

            for(int i = 0; i<imena.length;i++)
            {
                nogometasDataSource.addNogometasToDb(imena[i], urlovi[i]);
            }
            Log.d("Pos", String.valueOf(pos));
        }

        /** Prikaz trenutne baze */
        dbase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbmanager = new Intent(MainActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        /** Prikaz ostvarenih rezultata */
        seeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbmanager = new Intent(MainActivity.this, ScoreListActivity.class);
                startActivity(dbmanager);
            }
        });

        /** Unos imena korisnika */
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    playerName = s.toString();
                    if (s.toString().equals("baza")) {
                        dbase.setVisibility(View.VISIBLE);
                    } else
                        dbase.setVisibility(View.INVISIBLE);
                    checkValidation();
                }
            }
        });

        /** Biranje željene razine*/
        setLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                difficulty = progress;
                switch (progress) {
                    case 0:
                        yourLevel.setText("Easy");
                        difficulty = 0;
                        break;
                    case 1:
                        yourLevel.setText("Medium");
                        difficulty = 1;
                        break;
                    case 2:
                        yourLevel.setText("Advanced");
                        difficulty = 2;
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /** Ulaz u activity pogađanje nogometasa*/
        footballer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String korisnik = name.getText().toString();
                Intent intent = new Intent(MainActivity.this, FootballerActivity.class);
                intent.putExtra("name", playerName);
                intent.putExtra("level", difficulty);
                startActivity(intent);
            }
        });
    }

    /** Provjera imena korisnika */
    public void checkValidation() {
        if (playerName.length() > 2 && playerName.length() < 15 ) {
            validation.setText("");
            footballer.setEnabled(true);

            /** Ako je krivo ime nemoj dopustiti da može prijeći u novi activity */
        } else {
            if (playerName.length() <= 3) {
                validation.setText("Too short name");
            } else if (playerName.length() > 15) {
                validation.setText("Too long name");
            }
            validation.setTextColor(Color.RED);
            /** Disable-aj botun */
            footballer.setEnabled(false);
        }
    }
}