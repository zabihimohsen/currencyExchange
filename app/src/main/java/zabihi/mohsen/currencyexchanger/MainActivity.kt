package zabihi.mohsen.currencyexchanger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import zabihi.mohsen.currencyexchanger.databinding.ActivityMainBinding
import zabihi.mohsen.currencyexchanger.mainactivity.BalanceRecyclerViewAdapter
import zabihi.mohsen.currencyexchanger.mainactivity.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var  job : Job
    private lateinit var recyclerViewAdapter: BalanceRecyclerViewAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //working with recyclerview
        binding.balancesRV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewAdapter = BalanceRecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }

        viewModel.getBalancesObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })
        //check for submit button
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
                        builder.setMessage(event.result)
                        builder.setNeutralButton(this@MainActivity.getString(R.string.ok)) { dialog, which ->

                        }
                        builder.show()
                    }
                    is MainViewModel.CurrencyEvent.LoadingStatus -> {
                    }
                    else -> Unit // init state
                }
            }

        }

        // start fetching from server
        job = viewModel.repeatFun()
        job.start()
    }


}