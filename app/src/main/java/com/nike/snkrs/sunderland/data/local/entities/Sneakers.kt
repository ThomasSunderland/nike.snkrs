/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.data.local.entities


//region import directives

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nike.snkrs.sunderland.R

//endregion import directives


/**
 * Encapsulates the local database "sneakers" table
 * @author Thomas Sunderland. 2021 MAY 14
 */
@Entity(tableName = "sneakers")
data class Sneakers(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "resource") val resource: String,
    @ColumnInfo(name = "source") val source: String
) {

    companion object {

        /**
         * initial set of data
         */
        val SNEAKERS_INIT_DATA = listOf(
            Sneakers(resource = R.drawable.sneakers_01.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_02.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_03.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_04.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_05.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_06.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_07.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_08.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_09.toString(), source = "nike.com"),
            Sneakers(resource = R.drawable.sneakers_10.toString(), source = "nike.com")
        )
    }
}
