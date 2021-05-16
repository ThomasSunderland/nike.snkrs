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
import kotlinx.coroutines.flow.Flow

//endregion import directives


/**
 * Data Access Object (DAO) for the "apparel" table
 * @author Thomas Sunderland. 2021 MAY 14
 */
@Dao
interface ApparelDao {

    //region (c)rud (create)

    /**
     * Inserts a collection of Apparel records into our "apparel" table
     * Note: used to pre-populate table with data
     * @param records Records to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(records: List<Apparel>)
    //endregion (c)rud (create)


    //region c(r)ud (read/receive)

    /**
     * Asynchronously queries the "apparel" table for all records and returns an observable Flow
     */
    @Query("SELECT * FROM apparel ORDER BY uid")
    fun queryAll(): Flow<List<Apparel>>
    //endregion c(r)ud (read/receive)


    //region cru(d) (delete)

    /**
     * Deletes all of the records from the "apparel" table
     */
    @Query("DELETE FROM apparel")
    fun deleteAll()
    //endregion cru(d) (delete)
}