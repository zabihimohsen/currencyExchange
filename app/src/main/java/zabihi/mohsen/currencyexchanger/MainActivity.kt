package zabihi.mohsen.currencyexchanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import zabihi.mohsen.currencyexchanger.databinding.ActivityMainBinding
import zabihi.mohsen.currencyexchanger.mainactivity.MainViewModel
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var  job : Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitBtn.setOnClickListener{
            viewModel.convert(
                binding.sellET.text.toString(),
                binding.sellSP.selectedItem.toString(),
                binding.buySP.selectedItem.toString()
            )
        }
        lifecycleScope.launchWhenCreated {
            viewModel.conversion.collect{event->
                when(event){
                    is MainViewModel.CurrencyEvent.Message -> {
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("Done")
                        builder.setMessage(event.result)
                        builder.setNeutralButton("Ok") { dialog, which ->

                        }
                        builder.show()
                    }

                    is MainViewModel.CurrencyEvent.LoadingStatus -> {
                    }
                    else -> Unit // init state
                }
            }

        }
        job = viewModel.repeatFun()
        job.start()
    }


}