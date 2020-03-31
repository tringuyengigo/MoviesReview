package gdsvn.tringuyen.moviesreview.presentation.ui.activity.book

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import gdsvn.tringuyen.moviesreview.R

class BookingTicketActivity : AppCompatActivity() {

    var rowSeat = 10
    var colSeat = 10
    var seatGaping = 10
    var matrixSeatSize = rowSeat * colSeat
    var seatSize = 100

    var layout: ViewGroup? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_ticket)
        initializeSeats()
    }

    private fun initializeSeats() {

    }


}