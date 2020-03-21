package com.brskzr.todolist.components

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.brskzr.todolist.R
import kotlinx.android.synthetic.main.cv_datepicker.view.*
import java.util.*



class DateTimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes), OnPickHandler {

    private val timePicker: TimePickerFragment? = null
    private val datePicker: DatePickerFragment? = null

    init {
        initViews()
    }

    private fun initViews(){
        LayoutInflater.from(context).inflate(R.layout.cv_datepicker, this, true)
        this.orientation = VERTICAL
        this.isClickable = true
        cv_datepicker_container.setOnClickListener {
            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
            openDateDialog()  }
    }

    override fun onPick(sender: DialogFragment, selected: String) {
        when(sender) {
            is DatePickerFragment -> {
                tvDate.setText(selected)
                openTimeDialog()
            }
            is TimePickerFragment -> {
                tvTime.setText(selected)
            }
        }
    }

    private fun openDateDialog(){
        (context as AppCompatActivity)?.let {
            DatePickerFragment(this).open(it.supportFragmentManager)
        }
    }

    private fun openTimeDialog() {
        (context as AppCompatActivity)?.let {
            TimePickerFragment(this).open(it.supportFragmentManager,0, 0)
        }
    }
}

interface OnPickHandler{
    fun onPick(sender: DialogFragment, selected:String)
}

fun Int.padding(length: Int): String{
    return this.toString().padStart(length, '0')
}

class DatePickerFragment(val handler: OnPickHandler) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var year : Int = 0
    private var month: Int = 0
    private var day = 0
    private val calendar
        get() = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val picker = DatePickerDialog(activity!!, this, year, month, day)
        picker.datePicker.minDate = calendar.timeInMillis
        return picker
    }

    override fun onDateSet(view: DatePicker, y: Int, m: Int, d: Int) {
        val yyyy = y.padding(4)
        val mm = m.padding(2)
        val dd = d.padding(2)
        val date = "$dd.$mm.$yyyy"

        year = y
        month = m
        year = y

        handler.onPick(this, date)
    }

    fun open(fragmentManager: FragmentManager, day: Int = 0, month: Int = 0, year: Int = 0) {
        this.year = year
        this.month = month
        this.day = day

        if(day == 0 || month == 0 || year == 0){
            this.year = calendar.get(Calendar.YEAR)
            this.month = calendar.get(Calendar.MONTH)
            this.day = calendar.get(Calendar.DAY_OF_MONTH)
        }

        show(fragmentManager, "datepicker")
    }
}

class TimePickerFragment(val handler: OnPickHandler) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var hour: Int = 0
    private var minute: Int = 0
    private val calendar
        get() = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, min: Int) {
        val selectedTime = "${hourOfDay.padding(2)}:${minute.padding(2)}"
        hour = hourOfDay
        minute = min

        handler.onPick(this, selectedTime)
    }

    fun open(fragmentManager: FragmentManager, hour:Int, minute: Int) {
        this.hour = calendar.get(Calendar.HOUR_OF_DAY)
        this.minute = calendar.get(Calendar.MINUTE)

        if(hour > this.hour){
            this.hour = hour
        }

        show(fragmentManager, "timepicker")
    }
}