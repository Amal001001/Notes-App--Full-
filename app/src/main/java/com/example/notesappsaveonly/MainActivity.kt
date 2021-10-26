package com.example.notesappsaveonly

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var db:DBHlper
    lateinit var rv:RecyclerView
    lateinit var adapter:Adapter
    lateinit var et:EditText
    lateinit var button:Button

    lateinit var items : ArrayList<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DBHlper(this)

        items = arrayListOf()
        rv = findViewById(R.id.rv)

        et = findViewById(R.id.et)
        button = findViewById(R.id.button)
        button.setOnClickListener { postNote()
            updateRV()}

        updateRV()
    }

    private fun updateRV(){
        items = db.viewNotes()
        adapter = Adapter(this,items)
        adapter.update(items)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    fun postNote(){
        if(et.text.toString() != "") {
            db.saveData(Notes(0, et.text.toString()))
            et.text.clear()
            Toast.makeText(applicationContext, "note added", Toast.LENGTH_LONG).show()
            updateRV()
        }
    }

    fun update(Id:Int,Note:String){
        db.updateNote(Notes(Id,Note))
        updateRV()
    }

    fun delete(Id:Int){
        db.deleteNote(Notes(Id,""))
        updateRV()
    }

    fun raiseDialog(Id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> update(Id, updatedNote.text.toString()) })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel() })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }

    @SuppressLint("SetTextI18n")
    fun deleteDialog(Id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val confirmDelete = TextView(this)
        confirmDelete.text = "  Are you sure you want to delete this note?"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    _, _ -> delete(Id) })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel() })
        val alert = dialogBuilder.create()
        alert.setTitle("Delete Note")
        alert.setView(confirmDelete)
        alert.show()
    }
}