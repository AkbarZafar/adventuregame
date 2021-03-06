package com.heekostudios.explorerquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.util.Random;
import pl.droidsonroids.gif.GifDrawable;

public class Fight extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String user;
    int enemyhp, enemymax, HP, attack, defence, maxhp, difficulty, enemy, lifesteal, experience;
    Character result = 'a';
    AnimationSet enemyattack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        unload();
        setup();
    }

    public void unload() {
        HP = sharedPreferences.getInt("HP", 100);
        defence = sharedPreferences.getInt("defence", 1);
        attack = sharedPreferences.getInt("attack", 1);
        maxhp = sharedPreferences.getInt("maxhp", 1);
        difficulty = sharedPreferences.getInt("difficulty", 1);
        lifesteal = sharedPreferences.getInt("lifesteal", 0);
        experience = sharedPreferences.getInt("experience", 0);
        user=sharedPreferences.getString("user",user);
    }

    public void setup() {
        healthsetup();
        playergifsetup();
        enemypick();

        findViewById(R.id.won).setVisibility(View.INVISIBLE);

        TextView playername= findViewById(R.id.playername);
        playername.setText(user);
    }

    public void enemypick() {
        Random rnd = new Random();
        int i = rnd.nextInt(4);

        TextView enemyname= findViewById(R.id.enemyname);

        //int i=1;
        switch (i) {
            case 0: {
                enemy = 1;
                enemyname.setText(R.string.enemynamezombie);
                break;
            }
            case 1: {
                enemy = 2;
                enemyname.setText(R.string.enemynamerebel);
                break;
            }
            case 2: {
                enemy = 3;
                enemyname.setText(R.string.enemynameflyer);
                break;
            }
            case 3: {
                enemy = 4;
                enemyname.setText(R.string.enemynameuglyone);
                break;
            }
        }
        enemygifsetup();
    }

    public void enemygifsetup() {
        GifDrawable enemymonster;
        ImageView gifhold = findViewById(R.id.enemypicture);
        try {
            switch (enemy) {
                case 1: {
                    enemymonster = new GifDrawable(getResources(), R.drawable.zombieidle);
                    gifhold.setImageDrawable(enemymonster);
                    break;
                }
                case 2: {
                    enemymonster = new GifDrawable(getResources(), R.drawable.rebelidle);
                    gifhold.setImageDrawable(enemymonster);
                    break;
                }
                case 3: {
                    enemymonster = new GifDrawable(getResources(), R.drawable.zombieidle);
                    gifhold.setImageDrawable(enemymonster);
                    break;
                }
                case 4: {
                    enemymonster = new GifDrawable(getResources(), R.drawable.zombieidle);
                    gifhold.setImageDrawable(enemymonster);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playergifsetup() {
        try {
            GifDrawable warrior = new GifDrawable(getResources(), R.drawable.warrioridle);
            ImageView gifhold = findViewById(R.id.player);
            gifhold.setImageDrawable(warrior);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void healthsetup() {
        Random rander = new Random();
        enemymax = rander.nextInt(5) + (9 * difficulty);
        enemyhp = enemymax;

        healthupdate();
    }

    public void healthupdate() {

        int enemypercent = (enemyhp * 100) / enemymax;
        int HPpercent = (HP * 100) / maxhp*Constants.HP_INCREASE;

        ProgressBar playerhp = findViewById(R.id.playerhealth);
        ProgressBar enemy = findViewById(R.id.enemyhealth);

        TextView txtplayerhp = findViewById(R.id.txtplayerhealth);
        TextView txtenemyhp = findViewById(R.id.txtenemyhealth);

        playerhp.setProgress(HPpercent);
        enemy.setProgress(enemypercent);

        txtenemyhp.setText(enemyhp + getString(R.string.dash) + enemymax);
        txtplayerhp.setText(HP + getString(R.string.dash) + maxhp**Constants.HP_INCREASE);

    }

    public void bag(View x) {
        Intent bag = new Intent(getApplicationContext(), Bag.class);
        startActivity(bag);
        backhere();
    }

    public void enemyattack() {

        switch (enemy) {
            case 1: {
                zombieanimation();
                break;
            }
            case 2: {
                rebelanimation();
                break;
            }
            case 3: {
                zombieanimation();
                break;
            }
            case 4: {
                zombieanimation();
                break;
            }
        }

        Random rander = new Random();

        double dmg = rander.nextInt(4) + (4 * difficulty);
        if (defence == 5) {
            dmg *= (1 - 0.18);
        } else {
            dmg *= (1 - (defence * 0.03));
        }

        HP -= ((int) Math.round(dmg));
    }

    public void rebelanimation() {
        ImageView gifhold=findViewById(R.id.enemypicture);

        try {
            GifDrawable enemy = new GifDrawable(getResources(), R.drawable.rebelattack);
            gifhold.setImageDrawable(enemy);
        } catch (IOException e) {
            e.printStackTrace();
        }

        enemyattack = new AnimationSet(true);

        final TranslateAnimation enemymove = new TranslateAnimation(0, 375, 0, 0);
        enemymove.setDuration(750);


        enemyattack.addAnimation(enemymove);

        gifhold.startAnimation(enemyattack);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                findViewById(R.id.playerhitanimation).setVisibility(View.VISIBLE);
                fighttester();
            }
        }, 450);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                findViewById(R.id.playerhitanimation).setVisibility(View.GONE);
                enemygifsetup();
            }
        }, 800);

    }

    public void zombieanimation() {
        final ImageView enemy = findViewById(R.id.enemypicture);

        enemyattack = new AnimationSet(true);

        final TranslateAnimation enemymove = new TranslateAnimation(0, 375, 0, 0);
        enemymove.setDuration(650);

        final RotateAnimation enemyhit = new RotateAnimation(0, 15);
        enemyhit.setDuration(75);
        enemyhit.setStartOffset(550);


        enemyattack.addAnimation(enemymove);
        enemyattack.addAnimation(enemyhit);

        enemy.startAnimation(enemyattack);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                findViewById(R.id.playerhitanimation).setVisibility(View.VISIBLE);
                fighttester();
            }
        }, 450);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                findViewById(R.id.playerhitanimation).setVisibility(View.GONE);
            }
        }, 800);

    }

    public void runaway(View x) {
        Random rander = new Random();
        int i = rander.nextInt(6);
        TextView norun = findViewById(R.id.won);

        if (i == 0) {
            norun.setVisibility(View.GONE);
            success();
        } else {
            enemyattack();
            norun.setText(R.string.runfalse);
            norun.setVisibility(View.VISIBLE);
        }
    }

    public void playerhitanimation() {
        ImageView gifhold = findViewById(R.id.player);
        try {
            GifDrawable warrior = new GifDrawable(getResources(), R.drawable.swordswing);
            gifhold.setImageDrawable(warrior);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TranslateAnimation walkup = new TranslateAnimation(0, -375, 0, 0);
        walkup.setDuration(800);

        gifhold.startAnimation(walkup);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                findViewById(R.id.enemyhitanimation).setVisibility(View.VISIBLE);
                fighttester();
            }
        }, 500);


        walkup.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.enemyhitanimation).setVisibility(View.GONE);

                enabler();

                playergifsetup();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


    public void playerhit() {
        playerhitanimation();


        Random rander = new Random();

        int dmg;
        double lifestealheal;

        if (attack == 5) {
            dmg = rander.nextInt(4) + 35;
        } else {
            dmg = rander.nextInt(4) + (attack * 6);
        }
        enemyhp -= dmg;

        lifestealheal = dmg * (0.05 * lifesteal);
        HP += ((int) Math.round(lifestealheal));
    }

    public void hit(View x) {
        TextView norun = findViewById(R.id.won);
        norun.setVisibility(View.GONE);

        disabler();
        enemyattack();

        enemyattack.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (result != 'd') {
                    playerhit();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


    }

    public void enabler() {
        Button attackbtn = findViewById(R.id.Buttonhit);
        Button runbtn = findViewById(R.id.buttonrun);
        Button bagbtn = findViewById(R.id.buttonbag);

        attackbtn.setEnabled(true);
        runbtn.setEnabled(true);
        bagbtn.setEnabled(true);
    }

    public void disabler() {
        Button attackbtn = findViewById(R.id.Buttonhit);
        Button runbtn = findViewById(R.id.buttonrun);
        Button bagbtn = findViewById(R.id.buttonbag);

        attackbtn.setEnabled(false);
        runbtn.setEnabled(false);
        bagbtn.setEnabled(false);
    }

    public void fighttester() {
        if (HP <= 0) {
            HP = 0;
            //dead animation... lose animation...
            dead();
        } else if (enemyhp <= 0) {
            enemyhp = 0;
            enemydead();
            //dead animation... win animation...
            win();
        }
        healthupdate();
        updater();
    }

    public void enemydead() {
        ImageView deadnme = findViewById(R.id.enemypicture);

        switch (enemy) {
            case 1: {
                deadnme.setImageResource(R.drawable.zombiedead);
                break;
            }
            case 2: {
                deadnme.setImageResource(R.drawable.rebeldead);
                break;
            }
            case 3: {
                deadnme.setImageResource(R.drawable.zombiedead);
                break;
            }
            case 4: {
                deadnme.setImageResource(R.drawable.zombiedead);
                break;
            }
        }
    }

    public void updater() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HP", HP);
        editor.putInt("experience", experience);
        editor.apply();
    }

    public void backhere() {
        ImageView character = findViewById(R.id.player);
        TranslateAnimation setpostion = new TranslateAnimation(0, -100, 0, 0);


        setpostion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                unload();
                healthupdate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        setpostion.setDuration(500);


        character.startAnimation(setpostion);
    }

    public void win() {
        TextView won = findViewById(R.id.won);
        won.setText(R.string.playerwin);

        Random rander = new Random();
        experience += rander.nextInt(12) + 5;

        result = 'w';

        setuptwo();
    }

    public void dead() {
        TextView won = findViewById(R.id.won);
        won.setText(R.string.playerlost);

        result = 'd';

        ImageView playerdead = findViewById(R.id.player);
        playerdead.setImageResource(R.drawable.warriorlost);

        setuptwo();
    }

    public void success() {
        TextView won = findViewById(R.id.won);
        won.setText(R.string.truerun);

        Random rander = new Random();
        experience += rander.nextInt(5) + 1;

        result = 'r';

        setuptwo();
    }

    public void setuptwo() {
        Button hit = findViewById(R.id.Buttonhit);
        hit.setVisibility(View.GONE);

        Button bag = findViewById(R.id.buttonbag);
        bag.setVisibility(View.GONE);

        Button run = findViewById(R.id.buttonrun);
        run.setVisibility(View.GONE);

        Button resume = findViewById(R.id.Continue);
        resume.setVisibility(View.VISIBLE);

        TextView message = findViewById(R.id.won);
        message.setVisibility(View.VISIBLE);
    }

    public void exit(View x) {
        Intent home = new Intent(getApplicationContext(), Gamescreen.class);

        switch (result) {
            case ('w'): {
                startActivity(home);
                loot();
                break;
            }
            case ('d'): {
                Intent lose = new Intent(getApplicationContext(), Death.class);
                startActivity(lose);
                finish();
                break;
            }
            case ('r'): {
                startActivity(home);
                break;
            }
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    public void loot() {
        Intent item = new Intent(getApplicationContext(), Found.class);

        Random rander = new Random();
        int i = rander.nextInt(6);

        switch (i) {
            case 0:
            case 1: {
                for (int y = 0; y <= 1; y++) {
                    startActivity(item);
                }
                break;
            }
            case 2:
            case 3:
            case 4: {
                startActivity(item);
                break;
            }
            case 5: {

            }
        }
        finish();
    }

}
