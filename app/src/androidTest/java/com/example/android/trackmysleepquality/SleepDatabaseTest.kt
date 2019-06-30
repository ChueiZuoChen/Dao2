/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

//使用AndroidJUnit4類別執行測試
@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    //建立Dao和Database物件
    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    //在開始執行之前先執行並建立Database方法
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                //允許在主執行緒使用Query
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    //執行之後如果有拋出例外就把Database關掉
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //開始執行測試
    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        //隨便建立一個SleepNight物件來寫進去Database
        val night = SleepNight()
        sleepDao.insert(night)
        //建立一個變數去接取抓到的資料
        val tonight = sleepDao.getTonight()
        //檢查剛插入的物件是不是他的sleepQuiluty是等於-1,因為我們插入新的物件沒有給sleepQuility,所以他還是會是預設值-1
        //如果等於-1,則完成測試. 反之如果不等於-1他則會跑出IOException來通知測試失敗
        assertEquals(tonight?.sleepQuility, -1)
    }
}
