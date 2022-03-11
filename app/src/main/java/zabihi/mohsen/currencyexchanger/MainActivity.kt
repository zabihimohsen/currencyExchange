package zabihi.mohsen.currencyexchanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import zabihi.mohsen.currencyexchanger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}