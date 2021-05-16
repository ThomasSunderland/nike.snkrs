/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.data.local.daos


//region import directives

import androidx.room.*
import com.nike.snkrs.sunderland.data.local.entities.Sneakers
import kotlinx.coroutines.flow.Flow

//endregion import directives


/**
 * Data Access Object (DAO) for the "sneakers" table
 * @author Thomas Sunderland. 2021 MAY 14
 */
@Dao
interface SneakersDao {

    //region (c)rud (create)

    /**
     * Inserts a collection of Sneakers records into our "sneakers" table
     * Note: used to pre-populate table with data
     * @param records Records to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(records: List<Sneakers>)
    //endregion (c)rud (create)


    //region c(r)ud (read/receive)

    /**
     * Asynchronously queries the "sneakers" table for all records and returns an observable Flow
     */
    @Query("SELECT * FROM sneakers ORDER BY uid")
    fun queryAll(): Flow<List<Sneakers>>
    //endregion c(r)ud (read/receive)


    //region cru(d) (delete)

    /**
     * Deletes all of the records from the "sneakers" table
     */
    @Query("DELETE FROM sneakers")
    fun deleteAll()
    //endregion cru(d) (delete)
}