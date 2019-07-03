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
 
package com.coding4ever.roedhi.myunittesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements MainView {

    EditText edtWidth, edtHeight, edtLength;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtWidth = findViewById(R.id.edt_width);
        edtHeight = findViewById(R.id.edt_height);
        edtLength = findViewById(R.id.edt_length);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        tvResult = findViewById(R.id.tv_result);

        final MainPresenter presenter = new MainPresenter(this);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String length = edtLength.getText().toString().trim();
                String width = edtWidth.getText().toString().trim();
                String height = edtHeight.getText().toString().trim();

                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(length)) {
                    isEmptyFields = true;
                    edtLength.setError("Field ini tidak boleh kosong");
                }

                if (TextUtils.isEmpty(width)) {
                    isEmptyFields = true;
                    edtWidth.setError("Field ini tidak boleh kosong");
                }

                if (TextUtils.isEmpty(height)) {
                    isEmptyFields = true;
                    edtHeight.setError("Field ini tidak boleh kosong");
                }

                if (!isEmptyFields) {
                    double l = Double.parseDouble(length);
                    double w = Double.parseDouble(width);
                    double h = Double.parseDouble(height);
                    presenter.calculateVolume(l, w, h);
                }
            }
        });

    }

    @Override
    public void showVolume(MainModel model) {
        tvResult.setText(String.valueOf(model.getVolume()));
    }
}
