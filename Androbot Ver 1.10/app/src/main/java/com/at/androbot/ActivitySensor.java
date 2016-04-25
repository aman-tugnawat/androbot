package com.at.androbot;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by AmanTugnawat on 18-04-16.
 */
public class ActivitySensor extends Activity implements SensorEventListener {

    SensorManager sensorManager = null;

    //for orientation values
    TextView outputX;
    TextView outputY;
    TextView outputZ;

    static float[] orientation,acc,meg,Rot,Inc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //just some textviews, for data output
        outputX = (TextView) findViewById(R.id.textView);
        outputY = (TextView) findViewById(R.id.textView2);
        outputZ = (TextView) findViewById(R.id.textView3);
        orientation=new float[3];
        acc=new float[3];
        meg=new float[3];
        Rot=new float[9];
        Inc=new float[9];



    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        synchronized (this) {


            switch (event.sensor.getType()){
                case Sensor.TYPE_ORIENTATION:
                    //outputX.setText("x:"+Float.toString(event.values[0]));
                    //outputY.setText("y:"+Float.toString(event.values[1]));
                    //outputZ.setText("z:"+Float.toString(event.values[2]));
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    acc[0]=event.values[0];
                    acc[1]=event.values[1];
                    acc[2]=event.values[2];
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    meg[0]=event.values[0];
                    meg[1]=event.values[1];
                    meg[2]=event.values[2];
                    break;

            }
            sensorManager.getRotationMatrix(Rot,Inc,acc,meg);
            sensorManager.getOrientation(Rot,orientation);
            outputX.setText("Z:"+Float.toString(orientation[0]/3.14f*180.0f));
            outputY.setText("X:"+Float.toString(orientation[1]/3.14f*180.0f));
            outputZ.setText("Y:"+Float.toString(orientation[2]/3.14f*180.0f));
        }

    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

    }
}
