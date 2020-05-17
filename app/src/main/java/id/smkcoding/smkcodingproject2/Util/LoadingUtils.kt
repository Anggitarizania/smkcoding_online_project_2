package id.smkcoding.smkcodingproject2.Util

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.smkcoding.smkcodingproject2.R

fun showLoading(context: Context, swipeRefreshLayout: SwipeRefreshLayout){
    swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimaryDark))

    swipeRefreshLayout.isEnabled = true
    swipeRefreshLayout.isRefreshing =true
}

fun dismissLoading(swipeRefreshLayout: SwipeRefreshLayout){
    swipeRefreshLayout.isRefreshing = false
    swipeRefreshLayout.isEnabled = false
}