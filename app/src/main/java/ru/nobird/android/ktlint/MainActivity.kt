package ru.nobird.android.ktlint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun explicitPublicTypeRuleViolation() =
        "explicit public type rule violation"

    fun expressionBodyRuleViolation1(): String = "expression body rule violation"

    fun expressionBodyRuleViolation2(): String {
        return "expression body rule violation"
    }

    fun expressionBodyIndentRuleViolation(): String =
            "expression body indent rule violation"
}
