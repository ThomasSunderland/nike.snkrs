/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.data.local


//region import directives

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nike.snkrs.sunderland.data.local.daos.ApparelDao
import com.nike.snkrs.sunderland.data.local.daos.AthletesDao
import com.nike.snkrs.sunderland.data.local.daos.SneakersDao
import com.nike.snkrs.sunderland.data.local.entities.Apparel
import com.nike.snkrs.sunderland.data.local.entities.Athletes
import com.nike.snkrs.sunderland.data.local.entities.Sneakers
import com.nike.snkrs.sunderland.util.ioThread
import com.nike.snkrs.sunderland.util.tryCatch

//endregion import directives


/**
 * Local data store consisting of our local sneakers, athletes, and apparel tables
 * @author Thomas Sunderland. 2021 MAY 14
 */
@Database(
    entities = [Sneakers::class, Athletes::class, Apparel::class],
    exportSchema = false,
    version = 1
)
abstract class EntityDatabase : RoomDatabase() {

    //region data access objects

    /**
     * Data Access Object (DAO) for the "sneakers" table
     */
    abstract fun sneakersDao(): SneakersDao

    /**
     * Data Access Object (DAO) for the "athletes" table
     */
    abstract fun athletesDao(): AthletesDao

    /**
     * Data Access Object (DAO) for the "apparel" table
     */
    abstract fun apparelDao(): ApparelDao
    //endregion data access objects


    //region database companion object

    /**
     * Database companion object
     */
    companion object {

        //region constants

        /**
         * Name of our entities database
         */
        private const val DATABASE_NAME = "entities.db"
        //endregion constants


        //region data members

        /**
         * Reference to our singleton database instance
         */
        @Volatile
        private var INSTANCE: EntityDatabase? = null
        //endregion data members


        //region private functions

        /**
         * Builds out our database and ensures that data is
         * pre-populated upon database creation
         */
        private fun buildDatabase(context: Context): EntityDatabase {
            return Room.databaseBuilder(context, EntityDatabase::class.java, DATABASE_NAME)
                // prepopulate the database after onOpen is called
                .addCallback(object : Callback() {

                    /**
                     * Called when the database has been opened.
                     * @param db Reference to the database.
                     */
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        // call base class implementation
                        super.onOpen(db)

                        // pre-populate the database with sneakers, athletes, and apparel data
                        // note: we do this on open rather than create because we're using resource ids which may change between builds
                        ioThread {
                            //@formatter:off
                            tryCatch {
                                // clear all data
                                getInstance(context).clearAllTables()

                                // sneakers data
                                getInstance(context).sneakersDao().insert(Sneakers.SNEAKERS_INIT_DATA)

                                // athletes data
                                getInstance(context).athletesDao().insert(Athletes.ATHLETES_INIT_DATA)

                                // apparel data
                                getInstance(context).apparelDao().insert(Apparel.APPAREL_INIT_DATA)
                            }
                            //@formatter:on
                        }
                    }
                }).build()
        }
        //endregion private functions


        //region public functions

        /**
         * Factory method for creating or retrieving the sole instance of our database
         */
        fun getInstance(context: Context): EntityDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        //endregion public functions
    }
    //endregion database companion object
}