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
 
package com.coding4ever.roedhi.myflexiblefragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionDialogFragment extends DialogFragment
        implements View.OnClickListener {

    Button btnChoose, btnClose;
    RadioGroup rgOptions;
    RadioButton rbSaf, rbMou, rbLvg, rbMoyes;

    public void setOptionDialogListener(OnOptionDialogListener optionDialogListener) {
        this.optionDialogListener = optionDialogListener;
    }

    OnOptionDialogListener optionDialogListener;

    public OptionDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnChoose = view.findViewById(R.id.btn_choose);
        btnChoose.setOnClickListener(this);

        btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);

        rgOptions = view.findViewById(R.id.rg_options);
        rbSaf = view.findViewById(R.id.rb_saf);
        rbLvg = view.findViewById(R.id.rb_lvg);
        rbMou = view.findViewById(R.id.rb_mou);
        rbMoyes = view.findViewById(R.id.rb_moyes);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                getDialog().cancel();
                break;

            case R.id.btn_choose:
                int checkedRadioButtonId = rgOptions.getCheckedRadioButtonId();
                if (checkedRadioButtonId != -1) {
                    String coach = null;
                    switch (checkedRadioButtonId) {
                        case R.id.rb_saf:
                            coach = rbSaf.getText().toString().trim();
                            break;
                        case R.id.rb_mou:
                            coach = rbMou.getText().toString().trim();
                            break;
                        case R.id.rb_lvg:
                            coach = rbLvg.getText().toString().trim();
                            break;
                        case R.id.rb_moyes:
                            coach = rbMoyes.getText().toString().trim();
                            break;
                    }

                    if (optionDialogListener != null) {
                        optionDialogListener.onOptionChosen(coach);
                    }

                    getDialog().dismiss();
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*Fragment fragment = getParentFragment();
        if (fragment instanceof DetailCategoryFragment) {
            DetailCategoryFragment detailCategoryFragment = (DetailCategoryFragment) fragment;
            this.optionDialogListener = detailCategoryFragment.optionDialogListener;
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.optionDialogListener = null;
    }

    public interface OnOptionDialogListener {
        void onOptionChosen(String text);
    }
}
