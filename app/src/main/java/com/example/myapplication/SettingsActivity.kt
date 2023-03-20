package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var sharedPreferences: SharedPreferences
    private var pressureSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sharedPreferences = getPreferences(Context.MODE_PRIVATE) ?: return
    }

    override fun onResume() {
        super.onResume()
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressureSensor != null) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == pressureSensor!!.type) {
            with (sharedPreferences.edit()) {
                putFloat("pressure", event.values[0])
                apply()
            }
        }
        val pressurevalue = sharedPreferences.getFloat("pressure",0f)
        findViewById<TextView>(R.id.textView).text = pressurevalue.toString()

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}