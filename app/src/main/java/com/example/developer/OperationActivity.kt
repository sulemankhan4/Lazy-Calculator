package com.example.developer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_operation.*

class OperationActivity : AppCompatActivity() {

    var operationType: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)
        intent.extras?.run {
            operationType = getString("operation_type")
        }
        text_operation_type.text = when (operationType) {
            "add" -> {
                "Perform Addition"
            }
            "sub" -> {
                "Perform Subtraction"
            }
            "mul" -> {
                "Perform Multiplication"
            }
            else -> {
                "Perform Division"
            }
        }

    }
}