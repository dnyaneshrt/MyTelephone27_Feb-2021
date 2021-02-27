package com.tech.myandroidtelephony

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_msg.setOnClickListener {

            var status = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (status == PackageManager.PERMISSION_GRANTED) {

                msgDemo()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 10)

            }


        }
        btn_call.setOnClickListener {
            var status = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            if (status == PackageManager.PERMISSION_GRANTED) {

                callDemo()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 11)

            }

        }
        btn_whatsapp.setOnClickListener {

            try {
                var mobile_number = cpp.selectedCountryCode + et_mobile_number.editText?.text.toString()

                var intent=Intent()
                intent.data =
                    Uri.parse(
                        "http://api.whatsapp.com/send?phone=" + mobile_number+ "&text=" + et_message.editText?.text.toString()
                    )
                startActivity(intent)

            }catch (e:Exception)
            {
                Toast.makeText(this,"errror  :"+e.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun msgDemo() {
        var mobile_number = cpp.selectedCountryCode + et_mobile_number.editText?.text.toString()

        var list = mobile_number.split(",")
        for (number in list) {
            var smsmanager = SmsManager.getDefault()

            var send_intent = Intent(this, SendActivity::class.java)
            var pendingIntent1 = PendingIntent.getActivity(this, 0, send_intent, 0)


            var deliver_intent = Intent(this, DeliveredActivity::class.java)
            var pendingIntent2 = PendingIntent.getActivity(this, 0, deliver_intent, 0)

            smsmanager.sendTextMessage(
                number,
                null,
                et_message.editText?.text.toString(),
                pendingIntent1,
                pendingIntent2
            )


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            msgDemo()
        } else if (requestCode == 11 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            callDemo()

        } else {
            Toast.makeText(this, "user is not allowed", Toast.LENGTH_LONG).show()
        }
    }

    private fun callDemo() {
        var mobile_number = cpp.selectedCountryCode + et_mobile_number.editText?.text.toString()

        var intent = Intent()
        intent.action = Intent.ACTION_CALL
        intent.data = Uri.parse("tel:" + mobile_number)
        startActivity(intent)
    }
}