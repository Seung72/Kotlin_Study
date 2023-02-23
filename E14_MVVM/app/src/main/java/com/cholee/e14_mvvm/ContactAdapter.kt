package com.cholee.e14_mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    val contactItemClick: (Contact) -> Unit,
    val contactItemLongClick: (Contact) -> Unit)
    : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
        private var contacts: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.bind(contacts[pos])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.tv_name)
        private val number = itemView.findViewById<TextView>(R.id.tv_number)
        private val initial = itemView.findViewById<TextView>(R.id.tv_initial)

        fun bind(contact: Contact) {
            name.text = contact.name
            number.text = contact.number
            initial.text = contact.initial.toString()

            itemView.setOnClickListener {
                contactItemClick(contact)
            }
            itemView.setOnLongClickListener {
                contactItemLongClick(contact)
                true
            }
        }
    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

}