package com.tech.myandroidtelephony

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var smsmanager=SmsManager.getDefault()

        var send_intent= Intent(this,SendActivity::class.java)
        var pendingIntent1=PendingIntent.getActivity(this,11,send_intent,0)


        var deliver_intent= Intent(this,DeliveredActivity::class.java)
        var pendingIntent2=PendingIntent.getActivity(this,11,deliver_intent,0)


        var mobile_number=cpp.selectedCountryCode+et_mobile_number.editText?.text.toString()
        btn_msg.setOnClickListener {
            smsmanager.sendTextMessage(mobile_number,null,et_message.editText?.text.toString(),pendingIntent1,pendingIntent2)
        }
        btn_call.setOnClickListener {
        var intent=Intent()
            intent.action=Intent.ACTION_CALL
            intent.data= Uri.parse("tel:"+mobile_number)
            startActivity(intent)
        }




//        smsmanager.sendTextMessage()
    }
}