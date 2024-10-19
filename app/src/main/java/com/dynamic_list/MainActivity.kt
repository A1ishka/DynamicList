package com.dynamic_list

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        layout = findViewById(R.id.layout)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            val spannableTitle = SpannableString(menuItem.title)
            spannableTitle.setSpan(
                ForegroundColorSpan(Color.BLUE),
                0,
                spannableTitle.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            menuItem.title = spannableTitle
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                showColorPickerDialog()
                true
            }

            R.id.action_clear -> {
                clearComponents()
                true
            }

            R.id.action_exit -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showColorPickerDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_color_picker, null)
        val backgroundColorSpinner: Spinner = dialogView.findViewById(R.id.spinner_background_color)
        val textColorSpinner: Spinner = dialogView.findViewById(R.id.spinner_text_color)

        val colors = arrayOf(
            getString(R.string.red_color),
            getString(R.string.green_color),
            getString(R.string.blue_color),
            getString(R.string.black_color), getString(R.string.white_color),
            getString(R.string.pink_color), getString(R.string.gray_color)
        )
        val colorValues = arrayOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.BLACK,
            Color.WHITE,
            Color.MAGENTA,
            Color.LTGRAY
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        backgroundColorSpinner.adapter = adapter
        textColorSpinner.adapter = adapter

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.choose_the_color))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val selectedBackgroundColor =
                    colorValues[backgroundColorSpinner.selectedItemPosition]
                val selectedTextColor = colorValues[textColorSpinner.selectedItemPosition]
                val color = colors[backgroundColorSpinner.selectedItemPosition]
                addNewComponent(selectedBackgroundColor, selectedTextColor, color)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun addNewComponent(backgroundColor: Int, textColor: Int, color: String) {
        val button = Button(this)
        button.text = getString(R.string.new_colored_component, color)

        button.setBackgroundColor(backgroundColor)
        button.setTextColor(textColor)

        layout.addView(button)

        Toast.makeText(this, getString(R.string.component_added), Toast.LENGTH_SHORT).show()
    }

    private fun clearComponents() {
        layout.removeAllViews()
        Toast.makeText(this, getString(R.string.all_cleared), Toast.LENGTH_SHORT).show()
    }
}