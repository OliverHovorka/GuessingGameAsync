package hovorka.at.ac.htlstp.lab22;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Random;

public class ThreadCode extends Thread {

    private int length;
    private Handler handler;
    private int duration;
    private static Random rd = new Random();

    public ThreadCode(Handler handler, int length, int duration) {
        this.length = length;
        this.handler = handler;
        this.duration = duration;
    }

    @Override
    public void run() {


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

    }
}
