package net.coding4ever.roedhi.moviecatalogueuiux.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.coding4ever.roedhi.moviecatalogueuiux.R;
import net.coding4ever.roedhi.moviecatalogueuiux.helpers.LocaleManager;

public class PengaturanBahasaActivity extends AppCompatActivity {

    public static int RESULT_CODE = 110;
    LocaleManager localeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_bahasa);

        localeManager = new LocaleManager(this);

        final RadioGroup rgLanguage = findViewById(R.id.rg_language);

        RadioButton rdoLanguageSelected = localeManager.getLanguage().equals(LocaleManager.LANGUAGE_INDONESIA) ?
                (RadioButton)rgLanguage.findViewById(R.id.rdo_indonesia) : (RadioButton)rgLanguage.findViewById(R.id.rdo_english);

        rdoLanguageSelected.setChecked(true);

        rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int selectedRadioButtonID = rgLanguage.getCheckedRadioButtonId();
                RadioButton radioButton = rgLanguage.findViewById(selectedRadioButtonID);

                if (rgLanguage.indexOfChild(radioButton) == 0) { // bahasa indonesia
                    localeManager.setNewLocale(PengaturanBahasaActivity.this, LocaleManager.LANGUAGE_INDONESIA);
                } else {
                    localeManager.setNewLocale(PengaturanBahasaActivity.this, LocaleManager.LANGUAGE_ENGLISH);
                }

                setResult(RESULT_CODE);
                finish();
            }
        });
    }
}
