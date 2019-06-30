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

package com.example.android.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    /*
    * 首先要先了解我們所設定的SleepTrackerViewModelFactory需要兩個參數分別是1.context 2.Dao,
    * 傳入這兩個參數之後他會建立SleepTrackerViewModel
    * 步驟
    * 建立一個變數接context
    * 建立一個變數接Dao實體
    * 然後呼叫SleepTrackerViewModelFactory並傳入上面兩個參數建立ViewmoelFactory
    * 最後透過ViewModelProviders.of(this,ViewModelFactory).get(...)建立ViewModel物件
    * 然後透過DataBinding連接UI和ViewModel
    * */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)

        val viewModel =
                ViewModelProviders.of(this,viewModelFactory)
                        .get(SleepTrackerViewModel::class.java)

        binding.sleepTrackerViewModel = viewModel

        binding.lifecycleOwner = this



        return binding.root
    }
}
