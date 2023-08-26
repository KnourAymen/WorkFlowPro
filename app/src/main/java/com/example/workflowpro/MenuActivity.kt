package com.example.workflowpro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuActivity : AppCompatActivity() {

    private lateinit var businessGridView: GridView
    private lateinit var skipFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        businessGridView = findViewById(R.id.business_type_grid_view)
        skipFab = findViewById(R.id.skip_fab)
        businessGridView.adapter = CustomGridAdapter(this)

        skipFab.setOnClickListener {
            var intentToGreetingActivity = Intent(applicationContext, GreetingActivity::class.java)
            startActivity(intentToGreetingActivity)
        }

    }
}

class CustomGridAdapter(private var context: Context) : BaseAdapter() {

    private val businessTypeImagesArray = arrayOf(
        R.drawable.facility,
        R.drawable.agent,
        R.drawable.conveyor,
        R.drawable.cars,
        R.drawable.mosque,
        R.drawable.school,
        R.drawable.repair_service,
        R.drawable.businesses
    )

    private var businessTypeTitlesArray = arrayOf(
        R.string.facility_building,
        R.string.property_management,
        R.string.manufacturing,
        R.string.fleet,
        R.string.mosque,
        R.string.school,
        R.string.small_medium_business,
        R.string.other_general_maintenance
    )

    override fun getCount(): Int {
        return businessTypeImagesArray.size
    }

    override fun getItem(p0: Int): Any {
        return businessTypeImagesArray[p0]
    }

    override fun getItemId(p0: Int): Long {
        return businessTypeImagesArray[p0].toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.single_row, p2, false)
        }

        val theBusinessTypeImageView = view!!.findViewById<ImageView>(R.id.business_type_image_view)
        val theBusinessTypeTextView = view.findViewById<TextView>(R.id.business_type_text_view)

        theBusinessTypeImageView.setImageResource(businessTypeImagesArray[p0])
        theBusinessTypeTextView.setText(businessTypeTitlesArray[p0])

        return view
    }
}