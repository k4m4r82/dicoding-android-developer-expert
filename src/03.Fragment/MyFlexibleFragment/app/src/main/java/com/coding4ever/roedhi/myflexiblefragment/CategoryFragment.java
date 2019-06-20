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


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment
        implements View.OnClickListener {


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnDetailCategory = view.findViewById(R.id.btn_detail_category);
        btnDetailCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_detail_category){
            DetailCategoryFragment mDetailCategoryFragment = new DetailCategoryFragment();

            Bundle mBundle = new Bundle();
            mBundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle");

            String description = "Kategori ini akan berisi produk-produk lifestyle";
            mDetailCategoryFragment.setArguments(mBundle);
            mDetailCategoryFragment.setDescription(description);

            FragmentManager mFragmentManager = getFragmentManager();
            if (mFragmentManager!= null) {
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_container, mDetailCategoryFragment, DetailCategoryFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        }
    }
}
