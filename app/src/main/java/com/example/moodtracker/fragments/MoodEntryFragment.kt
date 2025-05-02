package com.example.moodtracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.R
import com.example.moodtracker.data.FakeMoodRepository
import com.example.moodtracker.data.Mood

class MoodEntryFragment : Fragment() {
    private val events = listOf("Work", "School", "Sport", "Music", "Meet", "Family")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText: EditText = view.findViewById(R.id.mood_entry_name)
        val feelRadioGroup: RadioGroup = view.findViewById(R.id.mood_entry_feel)
        val categorySpinner: Spinner = view.findViewById(R.id.mood_entry_category)
        val checkOneCheckBox: CheckBox = view.findViewById(R.id.mood_entry_check_one)
        val checkTwoCheckBox: CheckBox = view.findViewById(R.id.mood_entry_check_two)
        val checkThreeCheckBox: CheckBox = view.findViewById(R.id.mood_entry_check_three)
        val ratingBar: RatingBar = view.findViewById(R.id.mood_entry_rating)
        val importanceSwitch: Switch = view.findViewById(R.id.mood_entry_importance)
        val saveButton: Button = view.findViewById(R.id.mood_entry_save)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            events
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        saveButton.setOnClickListener {
            val description = nameEditText.text.toString().trim()

            if(description.isEmpty()){
                Toast.makeText(requireContext(), "Description cannot be empty...", Toast.LENGTH_LONG)
                return@setOnClickListener
            }

            val selectedFeelingId = feelRadioGroup.checkedRadioButtonId
            val feeling = if (selectedFeelingId != 1){
                view.findViewById<RadioButton>(selectedFeelingId).text.toString()
            }
            else{
                "Hard to say"
            }

            val type = categorySpinner.selectedItem.toString()

            val statements = mutableListOf<String>()
            if(checkOneCheckBox.isChecked){
                statements.add(checkOneCheckBox.text.toString())
            }
            if(checkTwoCheckBox.isChecked){
                statements.add(checkTwoCheckBox.text.toString())
            }
            if(checkThreeCheckBox.isChecked){
                statements.add(checkThreeCheckBox.text.toString())
            }

            val rating = ratingBar.rating

            val importance = importanceSwitch.isChecked

            val moodEntry = Mood(
                description = description,
                feeling = feeling,
                type = type,
                statements = statements,
                rating = rating,
                importance = importance
            )

            Log.d("data", moodEntry.toString())
            FakeMoodRepository.addMood(moodEntry)
            findNavController().navigate(R.id.action_moodEntryFragment_to_moodHistoryFragment)
        }
    }
}