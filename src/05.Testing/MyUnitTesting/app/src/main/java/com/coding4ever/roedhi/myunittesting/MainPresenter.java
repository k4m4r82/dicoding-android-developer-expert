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

public class MainPresenter {
    private MainView view;

    MainPresenter(MainView view) {
        this.view = view;
    }

    public double volume(double length, double width, double height) {
        // return length / width * height;
        return length * width * height;
    }

    public void calculateVolume(double length, double width, double height) {
        double volume = volume(length, width, height);
        MainModel model = new MainModel(volume);
        view.showVolume(model);
    }
}
