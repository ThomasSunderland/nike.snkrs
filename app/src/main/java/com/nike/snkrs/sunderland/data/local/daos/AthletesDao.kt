/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.data.local.daos


//region import directives

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nike.snkrs.sunderland.data.local.entities.Apparel
import com.nike.snkrs.sunderland.data.local.entities.Athletes
import kotlinx.coroutines.flow.Flow

//endregion import directives


/**
 * Data Access Object (DAO) for the "athletes" table
 * @author Thomas Sunderland. 2021 MAY 14
 */
@Dao
interface AthletesDao {

    //region (c)rud (create)

    /**
     * Inserts a collection of Athletes records into our "athletes" table
     * Note: used to pre-populate table with data
     * @param records Records to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(records: List<Athletes>)
    //endregion (c)rud (create)


    //region c(r)ud (read/receive)

    /**
     * Asynchronously queries the "athletes" table for all records and returns an observable Flow
     */
    @Query("SELECT * FROM athletes ORDER BY uid")
    fun queryAll(): Flow<List<Athletes>>
    //endregion c(r)ud (read/receive)


    //region cru(d) (delete)

    /**
     * Deletes all of the records from the "athletes" table
     */
    @Query("DELETE FROM athletes")
    fun deleteAll()
    //endregion cru(d) (delete)
}