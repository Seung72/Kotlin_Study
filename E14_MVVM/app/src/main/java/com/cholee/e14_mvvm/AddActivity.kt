package com.cholee.e14_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.cholee.e14_mvvm.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var binding: ActivityAddBinding
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        contactViewModel = ViewModelProvider(this@AddActivity).get(ContactViewModel::class.java)

        if(isContact(intent)) {
            with(binding) {
                etName.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
                etNumber.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            }
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        binding.btnAddContact.setOnClickListener{
            val name = binding.etName.text.toString().trim()
            val number = binding.etNumber.text.toString().trim()

            if (name.isEmpty() || number.isEmpty()) {

            } else {
                val initial = name[0]
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }
    }

    private fun isContact(intent: Intent): Boolean {
        return (intent != null
                && intent.hasExtra(EXTRA_CONTACT_NAME)
                && intent.hasExtra(EXTRA_CONTACT_NUMBER)
                && intent.hasExtra(EXTRA_CONTACT_ID))
    }
    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }
}