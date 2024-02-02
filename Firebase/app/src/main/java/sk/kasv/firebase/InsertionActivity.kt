package sk.kasv.firebase

import EmployeeModel
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSavedData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSavedData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().reference.child("Employees")

        btnSavedData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()

        if (empName.isEmpty() || empAge.isEmpty() || empSalary.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        val empId = dbRef.push().key ?: return

        val employee = EmployeeModel(empId, empName, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    etEmpName.text.clear()
                    etEmpAge.text.clear()
                    etEmpSalary.text.clear()
                } else {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error saving data: ${task.exception}")
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure separately
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Error saving data: $exception")
            }
    }
}
