package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var startTime: String
    private lateinit var endTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun dayToInt(value: String): Int {
        return when(value) {
            "Monday" -> 1
            "Tuesday" -> 2
            "Wednesday" -> 3
            "Thursday" -> 4
            "Friday" -> 5
            "Saturday" -> 6
            else -> {
                0
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val course = findViewById<TextInputEditText>(R.id.ed_course_name_add).text.toString().trim()
                val lecturer = findViewById<TextInputEditText>(R.id.ed_lecturer_add).text.toString().trim()
                val day = findViewById<Spinner>(R.id.day_spinner).selectedItem.toString()
                val dayToInt = dayToInt(day)
                val note = findViewById<TextInputEditText>(R.id.ed_note_add).text.toString().trim()
                val startCheck = findViewById<TextView>(R.id.tv_start_time_add).text == "Time"
                val endCheck = findViewById<TextView>(R.id.tv_end_time_add).text == "Time"

                if (course.isEmpty() || startCheck || endCheck) {
                    Toast.makeText(this, getString(R.string.input_empty_message), Toast.LENGTH_SHORT)
                } else {
                    viewModel.insertCourse(course, dayToInt, startTime, endTime, lecturer, note)
                    finish()
                    Toast.makeText(this, "Course has been added successfully", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()

        when(view.id) {
            R.id.iv_start_time_add -> {
                dialogFragment.show(supportFragmentManager, "startTimePicker")
            }
            R.id.iv_end_time_add -> {
                dialogFragment.show(supportFragmentManager, "endTimePicker")
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        when(tag) {
            "startTimePicker" -> {
                startTime = String.format(getString(R.string.time_default_formatter), hour, minute)
                findViewById<TextView>(R.id.tv_start_time_add).text = startTime
            }
            "endTimePicker" -> {
                endTime = String.format(getString(R.string.time_default_formatter), hour, minute)
                findViewById<TextView>(R.id.tv_end_time_add).text = endTime
            }
        }
    }
}