package zabihi.mohsen.currencyexchanger.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BalanceEntity::class], version = 2)
abstract class RoomAppDb: RoomDatabase() {


    abstract fun balanceDao(): BalanceDao?

    companion object {
        private var INSTANCE: RoomAppDb?= null
        fun getAppDatabase(context: Context): RoomAppDb? {

            if(INSTANCE == null ) {

                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                    context.applicationContext, RoomAppDb::class.java, "AppDBB"
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}