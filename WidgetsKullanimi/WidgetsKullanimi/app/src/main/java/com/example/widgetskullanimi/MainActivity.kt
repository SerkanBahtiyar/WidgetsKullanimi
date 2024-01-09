package com.example.widgetskullanimi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.widgetskullanimi.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var kontrol = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resimGoster("su.png")

        binding.buttonResim1.setOnClickListener {
            //binding.imageView.setImageResource(R.drawable.resim1)
            resimGoster("kofte.png")
        }

        binding.buttonResim2.setOnClickListener {
            //binding.imageView.setImageResource(
                //resources.getIdentifier("resim2","drawable",packageName))
            resimGoster("baklava.png")
        }

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Log.e("Sonuç","Switch : ON")
            }else{
                Log.e("Sonuç","Switch : OFF")
            }
        }

        binding.buttonBasla.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
        }

        binding.buttonDur.setOnClickListener {
            binding.progressBar.visibility = View.INVISIBLE
        }

        binding.slider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, ilerme: Int, fromUser: Boolean) {
                binding.textViewSlider.text = ilerme.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.buttonSaat.setOnClickListener {
            val tp = MaterialTimePicker.Builder()
                .setTitleText("Saat Seç")
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            tp.show(supportFragmentManager,"")

            tp.addOnPositiveButtonClickListener {
                binding.editTextSonuc.setText("${tp.hour} : ${tp.minute}")
            }
        }

        binding.buttonTarih.setOnClickListener {
            val dp = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Tarih Seç")
                .build()

            dp.show(supportFragmentManager,"")

            dp.addOnPositiveButtonClickListener {
                val df = SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault())
                val tarih = df.format(it)
                binding.editTextSonuc.setText(tarih)
            }
        }

        val ulkeler = ArrayList<String>()
        ulkeler.add("Türkiye")
        ulkeler.add("İtalya")
        ulkeler.add("Japonya")

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,ulkeler)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            kontrol = isChecked
            if(kontrol){
                val secilenButton = findViewById<Button>(binding.toggleButton.checkedButtonId)
                val buttonYazi = secilenButton.text.toString()
                Log.e("Sonuç",buttonYazi)
            }
        }

        binding.buttonToast.setOnClickListener {
            Toast.makeText(this,"Merhaba",Toast.LENGTH_SHORT).show()
        }

        binding.buttonAlert.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Başlık")
                .setMessage("Mesaj")
                .setPositiveButton("Tamam"){ i,d ->
                    Toast.makeText(this,"Tamam Seçildi",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("İptal"){ i,d ->
                    Toast.makeText(this,"İptal Seçildi",Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        binding.buttonGoster.setOnClickListener {
            Log.e("Sonuç","Switch Durum : ${binding.switch1.isChecked}")
            Log.e("Sonuç","Slider Durum : ${binding.slider.progress}")
            Log.e("Sonuç","Ülke         : ${binding.autoCompleteTextView.text}")
            if(kontrol){
                val secilenButton = findViewById<Button>(binding.toggleButton.checkedButtonId)
                val buttonYazi = secilenButton.text.toString()
                Log.e("Sonuç","Kategori : $buttonYazi")
            }
        }
    }

    fun resimGoster(resimAdi:String){
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/$resimAdi"
        Glide.with(this).load(url).override(150,150).into(binding.imageView)
    }
}