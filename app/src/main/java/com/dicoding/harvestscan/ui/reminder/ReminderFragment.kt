package com.dicoding.harvestscan.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.harvestscan.R
import com.google.android.material.textfield.TextInputEditText

class ReminderFragment : Fragment() {

    private lateinit var plantNameEditText: TextInputEditText
    private lateinit var reminderTimeEditText: TextInputEditText
    private lateinit var notesEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminder, container, false)

        // Initialize views
        plantNameEditText = view.findViewById(R.id.edit_text_plant_name)
        reminderTimeEditText = view.findViewById(R.id.edit_text_reminder_time)
        notesEditText = view.findViewById(R.id.edit_text_notes)

        // Set click listener for set reminder button
        val setReminderButton: Button = view.findViewById(R.id.setReminderButton)
        setReminderButton.setOnClickListener {
            // Call function to handle setting reminder
            setReminder()
        }

        return view
    }

    private fun setReminder() {
        // Get input values
        val plantName = plantNameEditText.text.toString().trim()
        val reminderTime = reminderTimeEditText.text.toString().trim()
        val notes = notesEditText.text.toString().trim()

        // Validate input
        if (plantName.isEmpty() || reminderTime.isEmpty() || notes.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Save reminder to database or perform any necessary action
        // Example: DatabaseHelper.saveReminder(plantName, reminderTime, notes)

        // Show success message
        Toast.makeText(requireContext(), "Reminder set successfully", Toast.LENGTH_SHORT).show()

        // Clear input fields
        plantNameEditText.text = null
        reminderTimeEditText.text = null
        notesEditText.text = null
    }
}
