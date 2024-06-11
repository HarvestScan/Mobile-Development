package com.dicoding.harvestscan.ui.reminder

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.dicoding.harvestscan.R
import java.util.Calendar
import android.app.TimePickerDialog


class ReminderFragment : Fragment() {

    private lateinit var editTextReminderTime: EditText
    private lateinit var editTextNotes: EditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminder, container, false)

        editTextReminderTime = view.findViewById(R.id.edit_text_reminder_time)
        editTextReminderTime.setOnClickListener {
            showTimePickerDialog()
        }

        editTextNotes = view.findViewById(R.id.edit_text_notes)
        editTextNotes.filters = arrayOf(InputFilter.LengthFilter(70))

        return view
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                editTextReminderTime.setText(String.format("%02d:%02d", hourOfDay, minute))
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}