package com.at.androbot;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * Created by AmanTugnawat on 17-04-16.
 */
public class NewControl extends Activity {

    static int control, speed, angle;
    final static int DEFAULT_SPEED=50;
    final static int DEFAULT_ANGLE=50;
    final static int DEFAULT_CONTROL=48;
    static int REFRESH_RATE=100;
    DataLoop mData;
    PowerManager.WakeLock mWakeLock;
    BluetoothService mBlueService = MainActivity.mBluetoothService;
    Button bhit,bDisconnect;
    ImageButton ib1, ib2, ib3, ib4, ib5, ib6;
    SeekBar speedBar,angleBar;

    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    public static final String TOAST = "toast";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // OnCreate
        super.onCreate(savedInstanceState);
        mData = new DataLoop(mBlueService);
        setContentView(R.layout.activity_newcontrol);
        ib1 = (ImageButton) findViewById(R.id.ib1);
        ib2 = (ImageButton) findViewById(R.id.ib2);
        ib3 = (ImageButton) findViewById(R.id.ib3);
        ib4 = (ImageButton) findViewById(R.id.ib4);
        //ib5 = (ImageButton) findViewById(R.id.ib5);
        //ib6 = (ImageButton) findViewById(R.id.ib6);
        bhit = (Button) findViewById(R.id.bhit);
        bDisconnect = (Button) findViewById(R.id.disconnect);
        speedBar= (SeekBar)findViewById(R.id.seekBar);
        speedBar.setProgress(DEFAULT_SPEED);
        angleBar=(SeekBar)findViewById(R.id.seekBarX);
        angleBar.setProgress(DEFAULT_ANGLE);

        ButtonInti();
        control = DEFAULT_CONTROL;
        speed = DEFAULT_SPEED;
        angle =DEFAULT_ANGLE;
        // PowerManger Wakelock
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Wake Lock");

        speedBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speed=seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        angleBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                angle=seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    void ButtonInti() {
        bDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlueService.stop();
                MainActivity.tv1.setText("You tried to disconnect.");
                finish();
            }
        });

        ib1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    control = DEFAULT_CONTROL;
                }else
                    control = 49;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                return true;

            }
        });

        ib2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    control = DEFAULT_CONTROL;
                }else
                    control = 50;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                return true;

            }
        });
        ib3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    control = DEFAULT_CONTROL;
                }else
                    control = 51;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                return true;

            }
        });
        ib4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    control = DEFAULT_CONTROL;
                }else
                    control = 52;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                return true;

            }
        });
        /*ib5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    control = 48;
                }else
                    control = 53;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                return true;

            }
        });
        ib6.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    control = 48;
                }else
                    control = 54;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                return true;

            }
        });*/
        bhit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    control = DEFAULT_SPEED;
                }else
                    control = 55;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                return true;

            }
        });

    }


    @Override
    protected void onPause() {
        // OnPause
        super.onPause();
        mWakeLock.release();
        mData.pause();
    }

    @Override
    protected void onResume() {
        // OnResume
        super.onResume();
        mWakeLock.acquire();
        mData.resume();
    }

    @Override
    protected void onStop() {
        // onStop
        super.onStop();
        if (mBlueService != null) {
            mBlueService.stop();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public class DataLoop implements Runnable {

        BluetoothService mBtService;
        boolean connected;
        float a, b;
        Thread mDataLoop;

        public DataLoop(BluetoothService mService) {
            mBtService = mService;
            connected = (mBtService.getState() == BluetoothService.STATE_CONNECTED);
        }

        public void pause() {
            connected = false;
            // To Stop the Data Thread
            while (true) {
                try {
                    mDataLoop.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            mDataLoop = null;
        }

        public void resume() {

            connected = (mBtService.getState() == BluetoothService.STATE_CONNECTED);
            mDataLoop = new Thread(this);
            mDataLoop.start();

        }

        public byte[] sendMessage(float u, float v, float w) {

            byte[] data = { 127, 0, 0, 0 };
            byte c, d, e;
            c = (byte) (u);
            d = (byte) (v);
            e = (byte) (w);
            data[1] = c;
            data[2] = d;
            data[3] = e;
            return data;

        }

        @Override
        public void run() {
            // run() for Data thread
            while (connected) {
                try {
                    mBtService.write(sendMessage(control, speed, angle));
                    Thread.sleep(REFRESH_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

