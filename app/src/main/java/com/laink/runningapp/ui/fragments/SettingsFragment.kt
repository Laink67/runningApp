package com.laink.runningapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.laink.runningapp.R
import com.laink.runningapp.other.Constants.KEY_NAME
import com.laink.runningapp.other.Constants.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFieldsFromSharedPreferences()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPreferences()

            if (success) {
                Snackbar.make(requireView(), "Saving is successful", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(
                    requireView(),
                    "Please, fill out all the fields",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun loadFieldsFromSharedPreferences() {
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 70f)
        etName.setText(name)
        etWeight.setText(weight.toString())
    }

    private fun applyChangesToSharedPreferences(): Boolean {
        val name = etName.text.toString()
        val weight = etWeight.text.toString()

        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }

        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .apply()

        val toolbarText = "Let's go $name"
        requireActivity().tvToolbarTitle.text = toolbarText

        return true
    }
}