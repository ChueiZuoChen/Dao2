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

package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* 1. 繼承RoomDatabase
* 2. 建立Database
* 3. 與Dao建立關聯
* 4. 獲得Database的引用
* */
@Database(entities = [SleepNight::class], version = 1,exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    abstract val sleepDatabaseDao:SleepDatabaseDao

    companion object{

        //建立一個資料變數INSTANCE初始化為NULL,當他在被呼叫後才建立實體
        @Volatile
        private var INSTANCE:SleepDatabase? = null

        fun getInstance(context: Context):SleepDatabase {
            //當呼叫需要抓到資料庫實體的時候,使用同步執行緒隨時存取
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    //利用變數instance去建立database的實體,再把他assign給INSTANCE
                    //使用Room.databaseBuilder方式建立實體,裡面放入 (傳進來的Context, Database的類別, Database的名字)
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"

                    ).fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }

        }

    }
}