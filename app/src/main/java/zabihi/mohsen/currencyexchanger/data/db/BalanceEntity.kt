package zabihi.mohsen.currencyexchanger.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances")
data class BalanceEntity(
    @PrimaryKey@ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "balance") val balance: Double,
)
