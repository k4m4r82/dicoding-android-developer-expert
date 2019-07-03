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
 
package net.coding4ever.roedhi.myrecyclerview;

import android.os.Parcel;
import android.os.Parcelable;

public class Hero implements Parcelable {
    private String name, from, photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.from);
        dest.writeString(this.photo);
    }

    public Hero() {
    }

    protected Hero(Parcel in) {
        this.name = in.readString();
        this.from = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<Hero> CREATOR = new Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };
}
