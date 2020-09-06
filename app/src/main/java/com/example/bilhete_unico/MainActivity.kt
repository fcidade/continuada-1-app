package com.example.bilhete_unico

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun calculate(comp: View) {
        toast("Calculando...")

        try {
            validateTransportName()
            validateNumberOfTravels()
            validateTransportPrice()

            calculatePrices()

        } catch (e: IllegalArgumentException) {
            toast(e.message ?: "Algo ruim aconteceu :/")
        }
    }

    fun calculatePrices() {
        val travelName = et_a1.text.toString()
        val qtdTravels = et_a2.text.toString().toDouble()
        val travelPrice = et_a3.text.toString().toDouble()
        val daysOfWorkInAMonth = 22

        val dailyPrice = qtdTravels * travelPrice
        val monthlyPrice = qtdTravels * travelPrice * daysOfWorkInAMonth

        tv_result_day.text = "R$ %.2f".format(dailyPrice)
        tv_result_month.text = "R$ %.2f".format(monthlyPrice)

        val cheapMessage = "Você está gastando bem pouco por mês com %s.".format(travelName)
        val normalMessage = "Você está gastando um valor normal por mês com %s".format(travelName)
        val expensiveMessage = "Nossa! Você está gastando muito por mês com %s :0".format(travelName)

        when (monthlyPrice.toDouble()) {
            in 0.0..250.0 -> showReaction(cheapMessage, Color.parseColor("#2E7D32"))
            in 250.0..300.0 -> showReaction(normalMessage, Color.parseColor("#0D47A1"))
            in 300.0..Double.MAX_VALUE -> showReaction(expensiveMessage, Color.parseColor("#b71c1c"))
        }

    }

    fun showReaction(message: String, color: Int) {
        tv_colored_message.text = message
        tv_colored_message.setTextColor(color)
    }

    fun validateTransportName() {
        if(et_a1.length() == 0)
            throw IllegalArgumentException("Você não pode utilizar um meio de transporte sem nome!")
    }

    fun validateNumberOfTravels() {
        if(et_a2.length() == 0 || et_a2.text.toString().toInt() <= 0)
            throw IllegalArgumentException("Você precisa realizar pelo menos uma viagem!")
    }

    fun validateTransportPrice() {
        if(et_a3.length() == 0)
            throw IllegalArgumentException("Você precisa informar o preço de uma passagem!")

        if(et_a3.text.toString().toDouble() <= 0.0)
            throw IllegalArgumentException("Essa passagem está barata demais :P")
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}