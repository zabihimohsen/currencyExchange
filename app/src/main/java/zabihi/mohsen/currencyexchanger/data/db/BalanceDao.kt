package zabihi.mohsen.currencyexchanger.data.db

import androidx.room.*

@Dao
interface BalanceDao {

    //Inserts data by overriding if there is old data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplace(entity: BalanceEntity)
    //Get all balance data sorted by amount
    @Query("SELECT * FROM balances ORDER BY balance DESC")
    fun getBalancesInfo(): List<BalanceEntity>?

    @Query("SELECT * FROM balances WHERE name = :name")
    fun getBalanceInfo(name:String): BalanceEntity?

    @Insert
    fun insertBalance(entity: BalanceEntity?)

    @Delete
    fun deleteBalance(entity: BalanceEntity?)

    @Update
    fun updateBalance(entity: BalanceEntity?)


}