package com.example.userdirectory

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private lateinit var toolbarMain: Toolbar
    private lateinit var nameET: EditText
    private lateinit var ageET: EditText
    private lateinit var saveBTN: Button
    private lateinit var usersLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Каталог пользователей"

        nameET = findViewById(R.id.nameET)
        ageET = findViewById(R.id.ageET)
        saveBTN = findViewById(R.id.saveBTN)

        usersLV = findViewById(R.id.usersLV)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<User>())
        usersLV.adapter = adapter

        userViewModel.userList.observe(this) { updatedList ->
            adapter.clear()
            adapter.addAll(updatedList)
            adapter.notifyDataSetChanged()
        }

        saveBTN.setOnClickListener {
            if (nameET.text.isEmpty() || ageET.text.isEmpty()) return@setOnClickListener
            val newUser = User(nameET.text.toString(), ageET.text.toString().toInt())
            userViewModel.addUser(newUser)
            adapter.notifyDataSetChanged()
            nameET.text.clear()
            ageET.text.clear()
        }

        usersLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                userViewModel.removeUser(position)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) finish()
        return super.onOptionsItemSelected(item)
    }
}