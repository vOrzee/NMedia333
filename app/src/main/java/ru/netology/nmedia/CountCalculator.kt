package ru.netology.nmedia

import java.math.RoundingMode
import java.text.DecimalFormat

object CountCalculator {
    fun calculate(count: Int): String {
        return when (count) {
            in 1000..1099 -> "${roundNoDecimal(count.toDouble() / 1_000.0)}K"
            in 1100..9_999 -> "${roundWithDecimal(count.toDouble() / 1_000.0)}K"
            in 10_000..999_999 -> "${roundNoDecimal(count.toDouble() / 1_000.0)}K"
            in 1_000_000..1_099_999 -> "${roundNoDecimal(count.toDouble() / 1_000_000.0)}M"
            in 1_100_000..Int.MAX_VALUE -> "${roundWithDecimal(count.toDouble() / 1_000_000.0)}M"

            else -> count.toString()
        }
    }


    fun roundWithDecimal(number: Double): Double? {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

    fun roundNoDecimal(number: Double): Int? {
        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toInt()
    }
}