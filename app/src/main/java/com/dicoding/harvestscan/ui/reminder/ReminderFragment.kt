package com.dicoding.harvestscan.ui.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.harvestscan.data.local.room.Reminder
import com.dicoding.harvestscan.databinding.FragmentReminderBinding
import com.dicoding.harvestscan.receiver.ReminderReceiver
import com.dicoding.harvestscan.ui.ViewModelFactory
import com.dicoding.harvestscan.viewmodel.ReminderViewModel
import kotlinx.coroutines.launch
import java.util.*

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextReminderTime.setOnClickListener {
            showTimePickerDialog(binding.editTextReminderTime)
        }

        binding.setReminderButton.setOnClickListener {
            saveReminder()
        }
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                editText.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun saveReminder() {
        val plantName = binding.editTextPlantName.text.toString().trim()
        val reminderTime = binding.editTextReminderTime.text.toString().trim()
        val notes = binding.editTextNotes.text.toString().trim()

        val daysOfWeek = getSelectedDays()

        if (plantName.isEmpty() || reminderTime.isEmpty() || daysOfWeek.isEmpty()) {
            Toast.makeText(context, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val reminder = Reminder(
            plantName = plantName,
            reminderTime = reminderTime,
            daysOfWeek = daysOfWeek,
            notes = notes
        )

        lifecycleScope.launch {
            viewModel.insertReminder(reminder)
            setAlarm(reminder) // Set the alarm after saving the reminder
            Toast.makeText(context, "Pengingat berhasil disimpan!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSelectedDays(): String {
        val days = mutableListOf<String>()
        if (binding.checkboxMonday.isChecked) days.add("Senin")
        if (binding.checkboxTuesday.isChecked) days.add("Selasa")
        if (binding.checkboxWednesday.isChecked) days.add("Rabu")
        if (binding.checkboxThursday.isChecked) days.add("Kamis")
        if (binding.checkboxFriday.isChecked) days.add("Jumat")
        if (binding.checkboxSaturday.isChecked) days.add("Sabtu")
        if (binding.checkboxSunday.isChecked) days.add("Minggu")

        return days.joinToString(", ")
    }

    private fun setAlarm(reminder: Reminder) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        reminder.daysOfWeek.split(", ").forEach { day ->
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("plantName", reminder.plantName)
                putExtra("notes", reminder.notes)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                day.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, reminder.reminderTime.split(":")[0].toInt())
                set(Calendar.MINUTE, reminder.reminderTime.split(":")[1].toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_WEEK, when (day) {
                    "Senin" -> Calendar.MONDAY
                    "Selasa" -> Calendar.TUESDAY
                    "Rabu" -> Calendar.WEDNESDAY
                    "Kamis" -> Calendar.THURSDAY
                    "Jumat" -> Calendar.FRIDAY
                    "Sabtu" -> Calendar.SATURDAY
                    "Minggu" -> Calendar.SUNDAY
                    else -> throw IllegalArgumentException("Invalid day")
                })
            }

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
                pendingIntent
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
