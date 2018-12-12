package com.example.capar.eventreminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val EXTRA_REPLY_TITLE: String = "com.example.android.eventreminder.REPLY_TITLE"
    val EXTRA_REPLY_DESCRIPTION: String = "com.example.android.eventreminder.REPLY_DESCRIPTION"
    val EXTRA_REPLY_ACTIVE: String = "com.example.android.eventreminder.REPLY_ACTIVE"
    val EXTRA_REPLY_PROGRESS: String = "com.example.android.eventreminder.REPLY_PROGRESS"

    val EXTRA_VIEW_TITLE: String = "com.example.android.eventreminder.VIEW_TITLE"
    val EXTRA_VIEW_DESCRIPTION: String = "com.example.android.eventreminder.VIEW_DESCRIPTION"
    val EXTRA_VIEW_ACTIVE: String = "com.example.android.eventreminder.VIEW_ACTIVE"

    private var NEW_EVENT_REQUEST_CODE = 1

    private var itemVisited: Int? = null

    private var events = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Bind RecyclerView and set up layoutManager for later use
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        rv.layoutManager = LinearLayoutManager(this)
        events = ArrayList()

        //If events previously existed, reinitialize them
        if(savedInstanceState != null) {
            if(savedInstanceState.getParcelableArrayList<Parcelable>("eventList") != null)
                events = savedInstanceState.getParcelableArrayList<Parcelable>("eventList") as ArrayList<Event>
        }

        //Give onClickListener to events in adapter and set adapter to RecyclerView
        var adapter = EventAdapter(events, { event: Event -> eventItemClicked(event) })
        rv.adapter = adapter

        //Add event creation functionality to 'add' floating action button
        fab.setOnClickListener { view ->
            addEvent()
        }

        //Implement item deletion on item swiped left
        val swipeHandler = object:SwipeToDeleteCallback(this) {
            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                adapter = rv.adapter as EventAdapter
                adapter.removeAt(p0.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv)

        //Check if user updated specific item in details activity
        updateEventIntentRecieved()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addEvent() {
        val intent = Intent(this, AddEventActivity::class.java)
        startActivityForResult(intent, NEW_EVENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // On return from event creation, check desired attributes and add event of that design to recyclerview
        if (requestCode == NEW_EVENT_REQUEST_CODE && resultCode == RESULT_OK) {
            val header: String? = data?.getStringExtra(EXTRA_REPLY_TITLE)
            val description: String? = data?.getStringExtra(EXTRA_REPLY_DESCRIPTION)
            val active: Boolean? = data?.getBooleanExtra(EXTRA_REPLY_ACTIVE, false)
            val progress: Int? = data?.getIntExtra(EXTRA_REPLY_PROGRESS, 0)
            events.add(Event(header!!, description!!, active!!, progress!!))
            recycler_view.adapter?.notifyDataSetChanged()
        }
    }

    fun eventItemClicked(event: Event) {
        Toast.makeText(this, "Clicked: ${event.title}", Toast.LENGTH_SHORT).show()
        itemVisited = events.indexOf(event)
        // When event listed in recyclerview is clicked, visit details page of that specific event to see more in depth information
        val showEventDetailActivity = Intent(this, EventDetailActivity::class.java)
        showEventDetailActivity.putExtra(EXTRA_VIEW_TITLE, event.title)
        showEventDetailActivity.putExtra(EXTRA_VIEW_DESCRIPTION, event.description)
        showEventDetailActivity.putExtra(EXTRA_VIEW_ACTIVE, event.active)
        startActivity(showEventDetailActivity)
    }

    fun updateEventIntentRecieved(){

        //Verify whether information has been updated with specific event
        val sharedPreferences = this.getSharedPreferences("event_state", Context.MODE_PRIVATE) ?: return
        //If so, update information in recyclerview
        if(events.size > 0 && itemVisited != null) {
            val header = sharedPreferences.getString("event_title", "ERROR")
            val description = sharedPreferences.getString("event_description", "DESCRIPTION NOT FOUND")
            val active = sharedPreferences.getBoolean("event_active", false)
            val progress = sharedPreferences.getInt("event_progress", 0)
            events[itemVisited!!] = Event(header!!, description!!, active, progress)
            recycler_view.adapter?.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        //If there are events, save their states before exiting activity
        if(events.size > 0) {

            outState?.putParcelableArrayList("eventList", events as ArrayList<out Parcelable>)

        }
        if(itemVisited != null) {
            outState?.putInt("item_visited", itemVisited!!)
        }
    }

}
