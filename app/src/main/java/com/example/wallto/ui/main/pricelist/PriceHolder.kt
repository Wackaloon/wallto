package com.example.wallto.ui.main.pricelist

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.wallto.R
import com.example.wallto.data.Currency
import java.text.DecimalFormat

class PriceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var image: ImageView = itemView.findViewById(R.id.ivIcon)
    private var name: TextView = itemView.findViewById(R.id.tvName)
    private var symbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private var price: TextView = itemView.findViewById(R.id.tvBalance)
    private var percent: TextView = itemView.findViewById(R.id.tvPercent)

    @SuppressLint("SetTextI18n")
    fun bind(currency: Currency) {
        val decimalFormat = DecimalFormat("##0.00")
        if (currency.percent_change_24h!!.substring(0, 1) == "-") {
            percent.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorRed))
            percent.setBackgroundResource(R.drawable.shape_redlight)
        }

        name.text = currency.name
        symbol.text = currency.symbol
        price.text = decimalFormat.format(currency.price_usd) + "$"
        percent.text = currency.percent_change_24h + "%"
        image.setImageDrawable(
            image.context.getDrawable(
                image.context.resources.getIdentifier(
                    currency.symbol!!.toLowerCase(), "drawable", image.context.packageName
                )
            )
        )
    }
}