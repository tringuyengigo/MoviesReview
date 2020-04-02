package gdsvn.tringuyen.moviesreview.presentation.ui.activity.book

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import gdsvn.tringuyen.moviesreview.R
import kotlinx.android.synthetic.main.activity_booking_ticket.*


class BookingTicketActivity : AppCompatActivity() {

    val ROWS = 11
    val COLUMNS = 12
    val tableLayout by lazy { TableLayout(this) }

    var c: Char = 'A'


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_ticket)
        createTable(ROWS, COLUMNS)
    }

    private fun initializeSeats() {
    }

    fun createTable(rows: Int, cols: Int) {

        for (i in 0 until rows) {

            val row = TableRow(this)
            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                0,
                0.1f
            )

            for (j in 0 until cols) {

                if(i == 0 && j == 0 ) {
                    val mTextView = TextView(this)
                    mTextView.apply {
                        layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT)
                        gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                        text = ""
                    }
                    row.addView(mTextView)
                    continue
                }

                if(i == 0) {
                    val mTextView = TextView(this)
                    mTextView.apply {
                        layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT)
                        gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                        text = "$j"
                    }
                    row.addView(mTextView)
                    continue
                }

                if(j == 0) {
                    val mTextView = TextView(this)
                    mTextView.apply {
                        layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT)
                        gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                        text = "$c"
                    }
                    c++
                    row.addView(mTextView)
                    continue
                }

                val mCheckBox = CheckBox(this)
                mCheckBox.apply {
                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
                    gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                    text = ""
                }
                row.addView(mCheckBox)
            }
            tb_seats.addView(row)
        }
        linearLayout.addView(tableLayout)
    }



}