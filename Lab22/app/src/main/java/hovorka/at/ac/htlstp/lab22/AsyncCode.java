package hovorka.at.ac.htlstp.lab22;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Random;

public class AsyncCode extends AsyncTask<Integer, Void, Void> {

    private Handler handler;
    private int duration;
    private static Random rd = new Random();

    public AsyncCode(Handler handler, int duration) {
        super();
        this.handler = handler;
        this.duration = duration;
    }

    @Override
    protected Void doInBackground(Integer... integers) {

        int length = integers[0];

        for(int i = 0; i < length; i++) {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putInt("code", Math.abs(rd.nextInt()%10)+1);
            msg.setData(b);
            handler.sendMessage(msg);
        }

        return null;
    }
}
