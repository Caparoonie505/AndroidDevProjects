package com.example.capar.eventreminder

import android.os.AsyncTask
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.item_card_view.view.*

class EventAdapter(val items: MutableList<Event>, val clickListener: (Event) -> Unit) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventAdapter.ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater
            .inflate(R.layout.item_card_view, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: EventAdapter.ViewHolder, p1: Int) {
        (p0).bind(items[p1], clickListener)
    }

    fun addItem(event: Event) {
        items.add(event)
        notifyItemInserted(items.size)
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    interface ClickLogic {
        fun onPositionClicked(position: Int)
        fun onLongClicked(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: Event, clickListener: (Event) -> Unit) {
            itemView.event_header.text = event.title
            itemView.event_allocated_time.progress = 0
            setSwitchLogic(event)
            itemView.setOnClickListener{ clickListener(event) }
        }

        private fun setSwitchLogic(event: Event){
            if(event.active == false) {
                itemView.active_switch.visibility = View.INVISIBLE
                itemView.completeButton.visibility = View.INVISIBLE
                event.active = false
            }
            itemView.active_switch.toggle()

            itemView.active_switch.setOnCheckedChangeListener{ buttonView, isChecked ->
                if (isChecked) {
                    //Switch is enabled
                    event.active = true
                    itemView.completeButton.visibility = View.VISIBLE

                } else {
                    event.active = false
                    //Switch is disabled
                    itemView.event_item_card.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorNoPriority
                        )
                    )
                    itemView.completeButton.visibility = View.INVISIBLE
                }

            }
        }

        companion object {
            class ProgressTask internal constructor(context: EventAdapter) : AsyncTask<Int, Int, Void> () {

                private var time: Int? = null


                override fun onPreExecute() {

                }

                override fun doInBackground(vararg p0: Int?): Void {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }



                override fun onProgressUpdate(vararg values: Int?) {
                    super.onProgressUpdate(*values)
                }
            }
        }

    }

}