package com.example.capar.eventreminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.event_detail_activity.*

class EventDetailActivity : AppCompatActivity() {

    val EXTRA_VIEW_TITLE: String = "com.example.android.eventreminder.VIEW_TITLE"
    val EXTRA_VIEW_DESCRIPTION: String = "com.example.android.eventreminder.VIEW_DESCRIPTION"
    val EXTRA_VIEW_ACTIVE: String = "com.example.android.eventreminder.VIEW_ACTIVE"
    val EXTRA_VIEW_PROGRESS: String = "com.example.android.eventreminder.VIEW_PROGRESS"

    val EXTRA_REPLY_TITLE: String = "com.example.android.eventreminder.REPLY_TITLE"
    val EXTRA_REPLY_DESCRIPTION: String = "com.example.android.eventreminder.REPLY_DESCRIPTION"
    val EXTRA_REPLY_ACTIVE: String = "com.example.android.eventreminder.REPLY_ACTIVE"
    val EXTRA_REPLY_PROGRESS: String = "com.example.android.eventreminder.REPLY_PROGRESS"


    var title: String? = null
    var description: String? = null
    var active: Boolean? = null
    var progress: Int? = null

    private var NEW_EVENT_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_detail_activity)

        var intentReceived = getIntent()

        if(intentReceived.hasExtra(EXTRA_VIEW_TITLE) &&
                intentReceived.hasExtra(EXTRA_VIEW_DESCRIPTION) &&
                intentReceived.hasExtra(EXTRA_VIEW_ACTIVE)) {
            title = intentReceived.getStringExtra(EXTRA_VIEW_TITLE)
            description = intentReceived.getStringExtra(EXTRA_VIEW_DESCRIPTION)
            active = intentReceived.getBooleanExtra(EXTRA_VIEW_ACTIVE,false)
            event_activity_header.text = title
            event_activity_description.text = description
        }

        fab_edit_details.setOnClickListener { view ->
            editEvent()
        }

        if(intentReceived.hasExtra(EXTRA_REPLY_TITLE) &&
                intentReceived.hasExtra((EXTRA_REPLY_DESCRIPTION)) &&
                intentReceived.hasExtra(EXTRA_REPLY_ACTIVE)){
            title = intentReceived.getStringExtra(EXTRA_REPLY_TITLE)
            description = intentReceived.getStringExtra(EXTRA_REPLY_DESCRIPTION)
            active = intentReceived.getBooleanExtra(EXTRA_REPLY_ACTIVE,true)
            event_activity_header.text = title
            event_activity_description.text = description
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_EVENT_REQUEST_CODE && resultCode == RESULT_OK) {
            var header: String? = data?.getStringExtra(EXTRA_REPLY_TITLE)
            var description: String? = data?.getStringExtra(EXTRA_REPLY_DESCRIPTION)
            var active: Boolean? = data?.getBooleanExtra(EXTRA_REPLY_ACTIVE, false)
            //Set temp global variables to nonnull values of replyintent
            this.title = header!!
            this.description = description!!
            this.active = active!!
            event_activity_header.text = header
            event_activity_description.text = description
        }
    }

    fun editEvent() {
        val intent = Intent(this, AddEventActivity::class.java)
        startActivityForResult(intent, NEW_EVENT_REQUEST_CODE)
    }

    fun returnToMain() {
        val sharedPreferences = getSharedPreferences("event_state", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("event_title", title)
        editor.putString("event_description", description)
        editor.putBoolean("event_active", active!!)
        editor.apply()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                returnToMain()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}