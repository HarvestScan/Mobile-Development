package com.dicoding.harvestscan.ui.menumyplant.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.Reminder
import com.dicoding.harvestscan.databinding.FragmentReminderBinding
import com.dicoding.harvestscan.ui.ViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var plantId: Int = -1
    private lateinit var plantList: List<Plant>
    private lateinit var plantSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPlantCountAndRedirect()
        requestNotificationPermission()

        plantSpinner = binding.spinnerPlantName

        viewModel.getAllPlants().observe(viewLifecycleOwner) { plants ->
            plantList = plants
            val plantNames = plants.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, plantNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            plantSpinner.adapter = adapter

            if (plantId != -1) {
                val selectedPlant = plants.find { it.id == plantId }
                selectedPlant?.let { plant ->
                    val position = adapter.getPosition(plant.name)
                    if (position != -1) {
                        plantSpinner.setSelection(position)
                    }
                }
            }
        }

        binding.editTextReminderTime.setOnClickListener {
            showTimePickerDialog(binding.editTextReminderTime)
        }

        binding.setReminderButton.setOnClickListener {
            saveReminder()
        }
    }
    private fun checkPlantCountAndRedirect() {
        viewModel.getAllPlants().observe(viewLifecycleOwner) { plants ->
            if (plants.isEmpty()) {
                showAlertDialog(getString(R.string.please_add_a_plant_first), R.id.action_navigation_reminder_to_navigation_my_plant)
            }
        }
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                editText.setText(String.format(getString(R.string._02d_02d), selectedHour, selectedMinute))
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun saveReminder() {
        val selectedPlantName = plantSpinner.selectedItem as String
        val selectedPlant = plantList.find { it.name == selectedPlantName }
        if (selectedPlant != null) {
            plantId = selectedPlant.id
        }

        val reminderTime = binding.editTextReminderTime.text.toString().trim()
        val notes = binding.editTextNotes.text.toString().trim()
        val daysOfWeek = getSelectedDays()

        if (plantId == -1 || reminderTime.isEmpty() || daysOfWeek.isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.all_fields_must_be_filled), Toast.LENGTH_SHORT).show()
            return
        }

        val reminder = Reminder(
            plantId = plantId,
            reminderTime = reminderTime,
            daysOfWeek = daysOfWeek,
            notes = notes
        )

        lifecycleScope.launch {
            viewModel.insertReminder(reminder)
            setAlarm(reminder) // Set the alarm after saving the reminder
            Toast.makeText(requireContext(),
                getString(R.string.reminder_successfully_saved), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.navigation_plants_menu, bundleOf("selectedTab" to 1))
        }
    }

    private fun getSelectedDays(): String {
        val days = mutableListOf<String>()
        if (binding.checkboxMonday.isChecked) days.add(getString(R.string.monday))
        if (binding.checkboxTuesday.isChecked) days.add(getString(R.string.tuesday))
        if (binding.checkboxWednesday.isChecked) days.add(getString(R.string.wednesday))
        if (binding.checkboxThursday.isChecked) days.add(getString(R.string.thursday))
        if (binding.checkboxFriday.isChecked) days.add(getString(R.string.friday))
        if (binding.checkboxSaturday.isChecked) days.add(getString(R.string.saturday))
        if (binding.checkboxSunday.isChecked) days.add(getString(R.string.sunday))

        return days.joinToString(", ")
    }

    private fun setAlarm(reminder: Reminder) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val selectedPlant = plantList.find { it.id == plantId }
        val plantName = selectedPlant?.name

        reminder.daysOfWeek.split(", ").forEach { day ->
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra(getString(R.string.plantname), plantName)
                putExtra(getString(R.string.notes), reminder.notes)
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
                    getString(R.string.monday) -> Calendar.MONDAY
                    getString(R.string.tuesday) -> Calendar.TUESDAY
                    getString(R.string.wednesday) -> Calendar.WEDNESDAY
                    getString(R.string.thursday) -> Calendar.THURSDAY
                    getString(R.string.friday) -> Calendar.FRIDAY
                    getString(R.string.saturday) -> Calendar.SATURDAY
                    getString(R.string.sunday) -> Calendar.SUNDAY
                    else -> throw IllegalArgumentException(getString(R.string.invalid_day))
                })
            }

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1)
            }

            Log.d("ReminderFragment", "Setting alarm for $day at ${calendar.timeInMillis}")

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
                pendingIntent
            )
            Log.d("ReminderFragment", "Alarm set for $day")
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }
    }


    private fun showAlertDialog(message: String, navigate: Int) {
        val dialog = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.you_still_don_t_have_any_plants))
            setMessage(message)
            setPositiveButton(getString(R.string.tvcontinue)) { _, _ ->
                findNavController().navigate(navigate)
            }
            create()
        }.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}