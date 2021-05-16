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
 * Encapsulates the local database "apparel" table
 * @author Thomas Sunderland. 2021 MAY 14
 */
@Entity(tableName = "apparel")
data class Apparel(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "resource") val resource: String,
    @ColumnInfo(name = "source") val source: String
) {

    //region companion object

    companion object {

        /**
         * initial set of data
         */
        val APPAREL_INIT_DATA = listOf(
            Apparel(resource = R.drawable.apparel_01.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_02.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_03.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_04.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_05.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_06.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_07.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_08.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_09.toString(), source = "nike.com"),
            Apparel(resource = R.drawable.apparel_10.toString(), source = "nike.com")
        )
    }
    //endregion companion object
}
