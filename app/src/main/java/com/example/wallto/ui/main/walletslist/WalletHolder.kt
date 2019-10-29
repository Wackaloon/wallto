package com.example.wallto.ui.main.walletslist

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.wallto.R
import com.example.wallto.data.Wallet

class WalletHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var image: ImageView = itemView.findViewById(R.id.ivIcon)
    private var name: TextView = itemView.findViewById(R.id.tvWalletName)
    private var symbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private var price: TextView = itemView.findViewById(R.id.tvBalance)

    @SuppressLint("SetTextI18n", "DefaultLocale")
    fun bind(wallet: Wallet) {
        if (wallet.balance == null) wallet.balance = "0"

        name.text = wallet.title
        symbol.text = wallet.type!!.toUpperCase()
        price.text = "${wallet.balance} ${wallet.type!!.toUpperCase()}"
        image.setImageDrawable(
            image.context.getDrawable(
                image.context.resources.getIdentifier(
                    wallet.type!!.toLowerCase(), "drawable", image.context.packageName
                )
            )
        )
    }
}