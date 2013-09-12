package com.play;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngan.play.R;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;

public class PlaySensorControl extends ControlExtension{
	private int bpm = 100;
    public static final int WIDTH = 128;
    public static final int HEIGHT = 128;
    private long timeOfTouch =-1;
    private long lastTouch =-1;
    private String message ="Loading...";
    private Runnable runner;
    private Context mContext;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.RGB_565;
    private boolean isVibrating;
    private TextView textBPM;

    public PlaySensorControl(Context context, String hostAppPackageName) {
		super(context, hostAppPackageName);
		mContext = context;


        message = "Feel the beat";


		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        isVibrating = false;
        updateDisplay();



    }
    public void startVibrate(){
        final int pulse = 60/bpm * 1000;
        final int duration = 100;
//        Runnable runner = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while(true){
//                        isVibrating = true;
//                        startVibrator(duration, 1, 1);
//                        wait(pulse);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    isVibrating = false;
//                    stopVibrator();
//                }
//            }
//        };
        if(hasVibrator()){
            isVibrating = true;
            if(pulse > duration)
                startVibrator(duration, pulse -duration ,1000);
            else
                startVibrator(duration, pulse, 1000);
            //isVibrating = false;
      //      runner.run();

        }


    }



    private void stopVibrate(){
        isVibrating = false;
        stopVibrator();
    }

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateDisplay();
	}

    @Override
    public void onTouch(ControlTouchEvent event) {
        try {
            switch(event.getAction()){
                case Control.Intents.TOUCH_ACTION_PRESS:
                    timeOfTouch = System.currentTimeMillis();
                    resetBPM();
                    updateDisplay();
                    //stopVibrate();
                    break;

//                case Control.Intents.TOUCH_ACTION_LONGPRESS:
//                    if(isVibrating)
//                        stopVibrator();
//                    else
//                        startVibrate();
//                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSwipe(int direction) {
        switch(direction){
            case Control.Intents.SWIPE_DIRECTION_DOWN:
                stopVibrate();
                break;
            case Control.Intents.SWIPE_DIRECTION_UP:
                startVibrate();
                break;
            case Control.Intents.SWIPE_DIRECTION_LEFT:
                changeBPM(-1);
                stopVibrate();
                break;
            case Control.Intents.SWIPE_DIRECTION_RIGHT:
                changeBPM(1);
                stopVibrate();
                break;
        }

    }

    private void changeBPM(int increase){
        if(increase > 0)
            bpm +=1;
        else if (increase < 0)
            bpm -=1;
        updateDisplay();

    }
    private void resetBPM() {
        if(lastTouch != -1){
            double difference = timeOfTouch - lastTouch;
            double divided = difference/1000;
            bpm = (int)(divided* 60);
            if(bpm == 0)
                bpm = 10;
            else if (bpm > 300)
                bpm = 300;
        }
        lastTouch = timeOfTouch;
    }


    public void updateDisplay(){
        try {
            Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, BITMAP_CONFIG);

            //Set up layout
            LinearLayout root = new LinearLayout(mContext);
            root.setLayoutParams(new LayoutParams(WIDTH, HEIGHT));


            LinearLayout layout = (LinearLayout)LinearLayout.inflate(mContext,
                    R.layout.main, root);

            textBPM = ((TextView)layout.findViewById(R.id.textBPM));
            textBPM.setText(Integer.toString(bpm));

                    ((TextView) layout.findViewById(R.id.textBPM))
            .setText(Integer.toString(bpm));
            layout.measure(WIDTH, HEIGHT);
            layout.layout(0, 0, layout.getMeasuredWidth(),
                        layout.getMeasuredHeight());

            Canvas canvas = new Canvas(bitmap);
            layout.draw(canvas);

            showBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
