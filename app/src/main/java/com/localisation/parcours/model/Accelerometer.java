package com.localisation.parcours.model;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

//Adapted mostly integrally from :  http://androidexample.com/Accelerometer_Basic_Example_-_Detect_Phone_Shake_Motion/index.php?view=article_discription&aid=109&aaid=131

public class Accelerometer {
    public interface AccelerometerListener {

        public void onAccelerationChanged(float x, float y, float z);

        public void onShake(float force);

    }

    static public int GetNumberOfStepSinceLast()
    {
        int returnValue = nStepsSinceLastCall;
        nStepsSinceLastCall = 0;
        return returnValue;
    }

    AccelerometerManager manager = new AccelerometerManager();
    float previousG = 0;
    static int nStepsSinceLastCall = 0;
    public class MainAccelerometer extends Activity implements AccelerometerListener{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            // Check onResume Method to start accelerometer listener
        }

        public void onAccelerationChanged(float x, float y, float z) {
            float g = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            if(g+1>previousG || g-1 < previousG)
            {
                nStepsSinceLastCall++;
                previousG = g;
            }
        }

        public void onShake(float force) {


            Toast.makeText(getBaseContext(), "Motion detected",
                    Toast.LENGTH_SHORT).show();

        }



        @Override
        public void onResume() {
            super.onResume();
            Toast.makeText(getBaseContext(), "onResume Accelerometer Started",
                    Toast.LENGTH_SHORT).show();

            //Check device supported Accelerometer senssor or not
            if (manager.isSupported(this)) {

                //Start Accelerometer Listening
                manager.startListening(this);
            }
        }

        @Override
        public void onStop() {
            super.onStop();

            //Check device supported Accelerometer senssor or not
            if (manager.isListening()) {

                //Start Accelerometer Listening
                manager.stopListening();

                Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
                        Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i("Sensor", "Service  distroy");

            //Check device supported Accelerometer senssor or not
            if (manager.isListening()) {

                //Start Accelerometer Listening
                manager.stopListening();

                Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }

    public class AccelerometerManager {

        private Context aContext=null;


        /** Accuracy configuration */
        private float threshold  = 15.0f;
        private int interval     = 200;

        private Sensor sensor;
        private SensorManager sensorManager;
        // you could use an OrientationListener array instead
        // if you plans to use more than one listener
        private AccelerometerListener listener;

        /** indicates whether or not Accelerometer Sensor is supported */
        private Boolean supported;
        /** indicates whether or not Accelerometer Sensor is running */
        private boolean running = false;

        /**
         * Returns true if the manager is listening to orientation changes
         */
        public boolean isListening() {
            return running;
        }

        /**
         * Unregisters listeners
         */
        public void stopListening() {
            running = false;
            try {
                if (sensorManager != null && sensorEventListener != null) {
                    sensorManager.unregisterListener(sensorEventListener);
                }
            } catch (Exception e) {}
        }

        /**
         * Returns true if at least one Accelerometer sensor is available
         */
        public boolean isSupported(Context context) {
            aContext = context;
            if (supported == null) {
                if (aContext != null) {


                    sensorManager = (SensorManager) aContext.
                            getSystemService(Context.SENSOR_SERVICE);

                    // Get all sensors in device
                    List<Sensor> sensors = sensorManager.getSensorList(
                            Sensor.TYPE_ACCELEROMETER);

                    supported = new Boolean(sensors.size() > 0);



                } else {
                    supported = Boolean.FALSE;
                }
            }
            return supported;
        }

        /**
         * Configure the listener for shaking
         * @param threshold
         *             minimum acceleration variation for considering shaking
         * @param interval
         *             minimum interval between to shake events
         */
        public void configure(int threshold, int interval) {
            manager.threshold = threshold;
            manager.interval = interval;
        }

        /**
         * Registers a listener and start listening
         * @param accelerometerListener
         *             callback for accelerometer events
         */
        public void startListening( AccelerometerListener accelerometerListener )
        {

            sensorManager = (SensorManager) aContext.
                    getSystemService(Context.SENSOR_SERVICE);

            // Take all sensors in device
            List<Sensor> sensors = sensorManager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER);

            if (sensors.size() > 0) {

                sensor = sensors.get(0);

                // Register Accelerometer Listener
                running = sensorManager.registerListener(
                        sensorEventListener, sensor,
                        SensorManager.SENSOR_DELAY_GAME);

                listener = accelerometerListener;
            }


        }

        /**
         * Configures threshold and interval
         * And registers a listener and start listening
         * @param accelerometerListener
         *             callback for accelerometer events
         * @param threshold
         *             minimum acceleration variation for considering shaking
         * @param interval
         *             minimum interval between to shake events
         */
        public void startListening(
                AccelerometerListener accelerometerListener,
                int threshold, int interval) {
            configure(threshold, interval);
            startListening(accelerometerListener);
        }

        /**
         * The listener that listen to events from the accelerometer listener
         */
        private SensorEventListener sensorEventListener =
                new SensorEventListener() {

                    private long now = 0;
                    private long timeDiff = 0;
                    private long lastUpdate = 0;
                    private long lastShake = 0;

                    private float x = 0;
                    private float y = 0;
                    private float z = 0;
                    private float lastX = 0;
                    private float lastY = 0;
                    private float lastZ = 0;
                    private float force = 0;

                    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

                    public void onSensorChanged(SensorEvent event) {
                        // use the event timestamp as reference
                        // so the manager precision won't depends
                        // on the AccelerometerListener implementation
                        // processing time
                        now = event.timestamp;

                        x = event.values[0];
                        y = event.values[1];
                        z = event.values[2];

                        // if not interesting in shake events
                        // just remove the whole if then else block
                        if (lastUpdate == 0) {
                            lastUpdate = now;
                            lastShake = now;
                            lastX = x;
                            lastY = y;
                            lastZ = z;
                            Toast.makeText(aContext,"No Motion detected",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            timeDiff = now - lastUpdate;

                            if (timeDiff > 0) {

                    /*force = Math.abs(x + y + z - lastX - lastY - lastZ)
                                / timeDiff;*/
                                force = Math.abs(x + y + z - lastX - lastY - lastZ);

                                if (Float.compare(force, threshold) >0 ) {
                                    //Toast.makeText(Accelerometer.getContext(),
                                    //(now-lastShake)+"  >= "+interval, 1000).show();

                                    if (now - lastShake >= interval) {

                                        // trigger shake event
                                        listener.onShake(force);
                                    }
                                    else
                                    {
                                        Toast.makeText(aContext,"No Motion detected",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                    lastShake = now;
                                }
                                lastX = x;
                                lastY = y;
                                lastZ = z;
                                lastUpdate = now;
                            }
                            else
                            {
                                Toast.makeText(aContext,"No Motion detected", Toast.LENGTH_SHORT).show();

                            }
                        }
                        // trigger change event
                        listener.onAccelerationChanged(x, y, z);
                    }

                };

    }
}
