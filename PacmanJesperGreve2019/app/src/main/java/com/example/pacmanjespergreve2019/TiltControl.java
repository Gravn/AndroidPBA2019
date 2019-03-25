package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

public class TiltControl extends View
{
    private float x,y;
    public TiltControl(Context context)
    {
        super(context);
        this.x = 0;
        this.y = 0;
        ((SensorManager)context.getSystemService(Context.SENSOR_SERVICE)).registerListener
                (
                        new SensorEventListener()
                        {
                            @Override
                            public void onSensorChanged(SensorEvent event)
                            {
                                x = event.values[0];
                                y = event.values[1];
                            }

                            @Override
                            public void onAccuracyChanged(Sensor sensor, int accuracy)
                            {

                            }
                        },
                        ((SensorManager)context.getSystemService(Context.SENSOR_SERVICE))
                                .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public float getX()
    {
        return x;
    }

    @Override
    public float getY()
    {
        return y;
    }
}
