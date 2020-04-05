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
import kotlinx.android.synthetic.main.activity_save_reminder.*
import kotlinx.android.synthetic.main.cv_datepicker.view.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*



class DateTimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes), OnPickHandler {

    private var dateTime: LocalDateTime = LocalDateTime.now()
    private val dateFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val timeFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val selectedDate: Date
        get() =  Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())

    init {
        initViews()
    }

    private fun initViews(){
        LayoutInflater.from(context).inflate(R.layout.cv_datepicker, this, true)
        this.orientation = VERTICAL
        this.isClickable = true
        cv_datepicker_container.setOnClickListener { openDateDialog()  }
        setDateTime()
    }

    fun initialDate(dateTime:LocalDateTime) {
        this.dateTime = dateTime
        setDateTime()
    }

    private fun setDateTime() {
        tvTime.setText(dateTime.format(timeFormatter))
        tvDate.setText(dateTime.format(dateFormatter))
    }

    override fun onPick(sender: DialogFragment, localDateTime: LocalDateTime) {
        this.dateTime = localDateTime
        setDateTime()
        if(sender is DatePickerFragment){
            openTimeDialog()
        }
    }

    private fun openDateDialog(){
        (context as? AppCompatActivity)?.let {
            DatePickerFragment(this).open(it.supportFragmentManager, dateTime)
        }
    }

    private fun openTimeDialog() {
        (context as? AppCompatActivity)?.let {
            TimePickerFragment(this).open(it.supportFragmentManager, dateTime)
        }
    }
}

interface OnPickHandler{
    fun onPick(sender: DialogFragment, localDateTime: LocalDateTime)
}

class DatePickerFragment(val handler: OnPickHandler) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var localDateTime: LocalDateTime

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val picker = DatePickerDialog(activity!!, this, localDateTime.year, localDateTime.monthValue - 1, localDateTime.dayOfMonth)
        return picker
    }

    override fun onDateSet(view: DatePicker, y: Int, m: Int, d: Int) {
        val selected = LocalDateTime.of(y, m + 1, d,
            localDateTime.hour,
            localDateTime.minute)
        handler.onPick(this, selected)
    }

    fun open(fragmentManager: FragmentManager, localDateTime: LocalDateTime) {
        this.localDateTime = localDateTime
        show(fragmentManager, "datepicker")
    }
}

class TimePickerFragment(val handler: OnPickHandler) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var localDateTime: LocalDateTime

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, this, localDateTime.hour, localDateTime.minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, min: Int) {

        val selected = LocalDateTime.of(localDateTime.year,
                                        localDateTime.month,
                                        localDateTime.dayOfMonth,
                                        hourOfDay,
                                        min)

        handler.onPick(this, selected)
    }

    fun open(fragmentManager: FragmentManager, localDateTime: LocalDateTime) {
        this.localDateTime = localDateTime
        show(fragmentManager, "timepicker")
    }
}