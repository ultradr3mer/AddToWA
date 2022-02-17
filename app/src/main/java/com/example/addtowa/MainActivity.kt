package com.example.addtowa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private var number = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            openWhatsappContact(number)
        }

        // Figure out what to do based on the intent type
        if (intent?.type == "text/x-vcard" && intent.clipData != null) {
            val contactUri = intent.clipData!!.getItemAt(0).uri
            val inputStream = contentResolver.openInputStream(contactUri)
            val r = BufferedReader(InputStreamReader(inputStream))
            val text = r.readText()
            var p: Pattern = Pattern.compile("TEL;.*?:(.*)" , Pattern.MULTILINE)
            val matcher = p.matcher(text)
            if(matcher.find()) {
                number = matcher.group(1)
                CenterText.text = number
            }
        }
    }

    fun openWhatsappContact(number: String) {
        val i = Intent(Intent.ACTION_VIEW,
            Uri.parse(
                String.format("https://api.whatsapp.com/send?phone=%s", number)
            )
        )
        startActivity(i)
    }
}