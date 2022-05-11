package com.example.composeui.viewmodel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SpinViewModel : ViewModel() {
    private val _time = MutableLiveData("")
    val time: LiveData<String> = _time

    @RequiresApi(Build.VERSION_CODES.N)
    fun selectedDataTime(context: Context) {
        var time = ""
        val currentDatetime = Calendar.getInstance()
        val startYear = currentDatetime.get(Calendar.YEAR)
        val startMonth = currentDatetime.get(Calendar.MONTH)
        val startDay = currentDatetime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDatetime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDatetime.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickerDateTime = Calendar.getInstance()
                pickerDateTime.set(year, month, day, hour, minute)
                val monthStr: String = if ((month + 1).toString().length == 1) {
                    "0${month + 1}"
                } else {
                    month.toString()
                }
                time = "$day - $monthStr - $year, $hour::$minute"
                updateDateTime(time)
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    private fun updateDateTime(dateTime: String) {
        _time.value = dateTime
    }
}