package com.example.wallto.ui.main.historylist

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.wallto.R
import com.example.wallto.data.History
// держать холдер в отдельном классе в целом неплохая идея, но можно его объявить и прямо внутри адаптера
// если он не очень большой (меньше 100 строк)
class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var value: TextView = itemView.findViewById(R.id.tvValue)
    private var status: TextView = itemView.findViewById(R.id.tvStatus)

    @SuppressLint("SetTextI18n")
    fun bind(history: History) {

        value.text = history.value
        status.text = history.send_colour

    }
}
