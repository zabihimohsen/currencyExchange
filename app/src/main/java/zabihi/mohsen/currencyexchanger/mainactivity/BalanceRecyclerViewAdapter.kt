package zabihi.mohsen.currencyexchanger.mainactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zabihi.mohsen.currencyexchanger.data.db.BalanceEntity
import zabihi.mohsen.currencyexchanger.databinding.BalanceItemBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class BalanceRecyclerViewAdapter :RecyclerView.Adapter<BalanceRecyclerViewAdapter.BalanceHolder>() {
    var datalist = ArrayList<BalanceEntity>()
    class BalanceHolder(private val binding: BalanceItemBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: BalanceEntity) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.HALF_EVEN
            binding.balanceTV.text = df.format(get.balance)
            binding.nameTV.text = get.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceHolder {
        val binding = BalanceItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BalanceHolder(binding)
    }

    override fun onBindViewHolder(holder: BalanceHolder, position: Int) {
        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
    fun setListData(data: ArrayList<BalanceEntity>) {
        this.datalist = data
    }

}