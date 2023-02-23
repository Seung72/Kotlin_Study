package com.cholee.e14_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cholee.e14_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val lm = LinearLayoutManager(this)
        val adapter = ContactAdapter({ contact ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        }, {contact ->
            deleteDialog(contact)
        })

        with(binding) {
            rvContact.adapter = adapter
            rvContact.layoutManager = lm
            rvContact.setHasFixedSize(true)
        }
        binding.btnAddContact.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        contactViewModel = ViewModelProvider(this@MainActivity).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>>{ contacts ->
            adapter.setContacts(contacts!!)
        })


    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("선택한 연락처를 지우시겠습니까?")
            .setPositiveButton("지우기") {_, _ ->
                contactViewModel.delete(contact)
            }
            .setNegativeButton("취소") {_, _ -> }
        builder.show()
    }
}