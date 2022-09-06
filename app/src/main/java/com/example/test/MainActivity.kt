package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelper = SQLiteHelper(this@MainActivity)

        initRecyclerView()

        binding.btnAdd.setOnClickListener { addStudent() }
        binding.btnView.setOnClickListener { getStudents() }
        binding.btnUpgrade.setOnClickListener { upgradeSudent() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            binding.editTextUsername.setText(it.name)
            binding.editTextEmail.setText(it.email)
            std = it
        }

        adapter?.setOnClickDeleteItem { deleteStudent(it.id) }
    }

    private fun deleteStudent(id: Int) {
        val builder = AlertDialog.Builder(this).apply {
            setMessage("Are you sure want to delete item?")
            setCancelable(true)
            setPositiveButton("Yes") { dialog, _ ->
                sqLiteHelper.deleteStudent(id)
                getStudents()
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alert = builder.create()
        alert.show()
    }

    private fun upgradeSudent(): Unit = with(binding){
        val name = editTextUsername.text.toString()
        val email = editTextEmail.text.toString()

        if (name == std?.name && email == std?.name){
            Toast.makeText(this@MainActivity, "Record not changed...", Toast.LENGTH_SHORT ).show()
            return
        }
        if (std == null) return
        if (name.isEmpty() || email.isEmpty()) Toast.makeText(this@MainActivity, "Please enter required field ", Toast.LENGTH_SHORT).show()
        else {
            val std = StudentModel(id = std!!.id, name = name, email = email)
            val status = sqLiteHelper.upgradeStudent(std)
            if (status > -1){
                clearEditText()
                getStudents()
            } else
                Toast.makeText(this@MainActivity, "Upgrade failed...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStudents() = with(binding){
        val stdList = sqLiteHelper.getAllStudents()
        Toast.makeText(this@MainActivity, "${stdList.size}", Toast.LENGTH_SHORT).show()

        adapter?.addItem(stdList)
    }

    private fun addStudent() = with(binding){
        val name = editTextUsername.text.toString()
        val email = editTextEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) Toast.makeText(this@MainActivity, "Please enter required field ", Toast.LENGTH_SHORT).show()
        else {
            val std = StudentModel(name = name, email = email)
            val status = sqLiteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this@MainActivity, "Student Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            }else Toast.makeText(this@MainActivity, "Record not saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearEditText() = with(binding){
        editTextUsername.setText("")
        editTextEmail.setText("")
        editTextUsername.requestFocus()
    }

    private fun initRecyclerView() = with(binding){
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }
}