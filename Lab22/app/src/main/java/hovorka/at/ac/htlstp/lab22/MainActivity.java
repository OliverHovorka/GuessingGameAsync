package hovorka.at.ac.htlstp.lab22;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private int lvl;
    private int[] code;
    private int[] usercode;
    private int stat = 0;
    private int userstat = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle b = msg.getData();
            int nr = b.getInt("code");
            code[stat++] = nr;
            TextView txt = findViewById(R.id.actnr);
            txt.setText(Integer.toString(nr));
        }
    };
    private Button reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("guessinggame", MODE_PRIVATE);
        lvl = preferences.getInt("level", 1);

        Button buttons[] = {findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3),
                findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6),
                findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9)};

        for(final Button btn: buttons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usercode[userstat++] = Integer.parseInt(btn.getText().toString());
                    TextView displaycode = findViewById(R.id.code);
                    displaycode.setText("Code: " + Arrays.toString(usercode));
                }
            });
        }

        reset  = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvl = 1;
            }
        });

        Button ok = findViewById(R.id.okbtn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(Arrays.equals(usercode, code));
                if(Arrays.equals(usercode, code)) {
                    lvl++;
                    restart();
                }
            }
        });

        restart();

    }

    public void restart() {
        code = new int[lvl+3];
        stat = 0;
        usercode = new int[lvl+3];
        userstat = 0;

        reset.setText("Reset Lvl: " + Integer.toString(lvl));

        if(lvl%2 == 0) {
            AsyncCode asyncCode = new AsyncCode(handler, 1000);
            asyncCode.execute(lvl+3);
        } else {
            ThreadCode tc = new ThreadCode(handler, lvl+3, 1000);
            tc.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = preferences.edit();
        ed.putInt("level", lvl);
        ed.apply();
    }
}
