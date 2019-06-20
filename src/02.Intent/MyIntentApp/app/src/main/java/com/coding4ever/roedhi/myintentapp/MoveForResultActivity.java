/**
 * Copyright (C) 2019 Kamarudin (http://coding4ever.net/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * The latest version of this file can be found at https://github.com/k4m4r82/dicoding-android-developer-expert
 */
 
package com.coding4ever.roedhi.myintentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MoveForResultActivity extends AppCompatActivity
        implements View.OnClickListener {

    Button btnChoose;
    RadioGroup rgNumber;

    public static String EXTRA_SELECTED_VALUE = "extra_selected_value";
    public static int RESULT_CODE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_for_result);

        btnChoose = findViewById(R.id.btn_choose);
        btnChoose.setOnClickListener(this);

        rgNumber = findViewById(R.id.rg_number);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_choose) {
            if (rgNumber.getCheckedRadioButtonId() != 0) {
                int value = 0;
                switch (rgNumber.getCheckedRadioButtonId()) {
                    case R.id.rb_50:
                        value = 50;
                        break;
                    case R.id.rb_100:
                        value = 100;
                        break;
                    case R.id.rb_150:
                        value = 150;
                        break;
                    case R.id.rb_200:
                        value = 200;
                        break;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_SELECTED_VALUE, value);
                setResult(RESULT_CODE, resultIntent);
                finish();
            }
        }
    }
}
