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
 * Encapsulates the local database "athletes" table
 * @author Thomas Sunderland. 2021 MAY 14
 */
@Entity(tableName = "athletes")
data class Athletes(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "resource") val resource: String,
    @ColumnInfo(name = "source") val source: String
) {

    companion object {

        /**
         * initial set of data
         */
        val ATHLETES_INIT_DATA = listOf(
            Athletes(resource = R.drawable.athlete_01.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_02.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_03.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_04.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_05.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_06.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_07.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_08.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_09.toString(), source = "Google"),
            Athletes(resource = R.drawable.athlete_10.toString(), source = "Google")
        )
    }
}
