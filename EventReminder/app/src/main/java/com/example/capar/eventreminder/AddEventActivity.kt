package com.example.capar.eventreminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.*

class AddEventActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val EXTRA_REPLY_TITLE: String = "com.example.android.eventreminder.REPLY_TITLE"
    val EXTRA_REPLY_DESCRIPTION: String = "com.example.android.eventreminder.REPLY_DESCRIPTION"
    val EXTRA_REPLY_ACTIVE: String = "com.example.android.eventreminder.REPLY_ACTIVE"
    val EXTRA_REPLY_PROGRESS: String = "com.example.android.eventreminder.REPLY_PROGRESS"

    private var editTextTitle: EditText? = null
    private var editTextDescription: EditText? = null

    private var progressChosen: Int? = null
    private var active: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_event_layout)

        editTextTitle = findViewById(R.id.event_header_edittext)
        editTextDescription = findViewById(R.id.event_description_edittext)
        val saveButton = findViewById<Button>(R.id.save_event_button)
        val timeSpinner: Spinner = findViewById(R.id.timeRequiredSpinner)

        //Fill desired time options for spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.time_declaration_array,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            //Specify the layout to use when list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Apply the adapter to the spinner
            timeSpinner.adapter = adapter
        }

        //TODO: Figure out how to hide the soft keyboard when spinner is opened not when an item is selected without crashing the app
        timeSpinner.onItemSelectedListener = this

        saveButton.setOnClickListener{
            hideKeyboard()
            saveEvent()
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Do nothing
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        //An item was selected. You can retrieve the selected item using p0.getItemAtPosition(p2)
        hideKeyboard()
        when (p0?.id) {
            R.id.timeRequiredSpinner -> {
                when (p2) {
                    0 -> progressChosen = 15
                    1 -> progressChosen = 30
                    2 -> progressChosen = 45
                    3 -> progressChosen = 60
                }
            }
        }
    }

    private fun saveEvent() {

        val replyIntent = Intent()
        if(TextUtils.isEmpty(editTextTitle?.text)){
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val title: String = editTextTitle?.text.toString()
            val description: String = editTextDescription?.text.toString()
            val progress: Int = progressChosen!!
            replyIntent.putExtra(EXTRA_REPLY_TITLE, title)
            replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION, description)
            replyIntent.putExtra(EXTRA_REPLY_ACTIVE, active)
            replyIntent.putExtra(EXTRA_REPLY_PROGRESS, progress)
            setResult(RESULT_OK, replyIntent)
        }
        finish()
        Toast.makeText(this, "Event Saved", Toast.LENGTH_LONG).show()

    }

    private fun getActiveState(view: View){

    }


}