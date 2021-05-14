package com.karolis.dailycalories;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private EditText weightKgEditText, heightCmEditText, ageYearsEditText;
    private EditText weightLbsEditText, heightFtEditText, heightInEditText;
    private Button calculateButton;
    private TextView bmiTextView, categoryTextView, bmrTextView, bmrCategory;
    private ToggleButton toggleUnitsButton;
    private CardView bmiResultCardView, bmrResultCardView;
    private Spinner dropDownMenu;
    private RadioGroup GenderHolder;
    private RadioButton selectMale, selectFemale;
    private String currentActivitiesPosition = "0";


    private boolean inMetricUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dropDownMenu = findViewById(R.id.dropdown_menu1);

        weightKgEditText = findViewById(R.id.activity_main_weightkgs);
        heightCmEditText = findViewById(R.id.activity_main_heightcm);

        weightLbsEditText = findViewById(R.id.activity_main_weightlbs);
        heightFtEditText = findViewById(R.id.activity_main_heightfeet);
        heightInEditText = findViewById(R.id.activity_main_heightinches);

        calculateButton = findViewById(R.id.activity_main_calculate);
        toggleUnitsButton = findViewById(R.id.activity_main_toggleunits);

        ageYearsEditText = findViewById(R.id.yearsET);
        selectFemale = findViewById(R.id.radio_Woman1);
        selectMale = findViewById(R.id.radio_Man1);

        bmiTextView = findViewById(R.id.activity_main_bmi);
        categoryTextView = findViewById(R.id.activity_main_category);
        bmrTextView = findViewById(R.id.activity_main_bmr);
        bmrCategory = findViewById(R.id.activity_bmr_category);

        bmiResultCardView = findViewById(R.id.activity_main_resultcard);
        bmrResultCardView = findViewById(R.id.activity_main_bmrresultcard);

        inMetricUnits = true;
        updateInputsVisibility();
        bmiResultCardView.setVisibility(View.GONE);
        bmrResultCardView.setVisibility(View.GONE);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             checkInputs();
             checkBMR();
            }

        });

        toggleUnitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inMetricUnits = !inMetricUnits;
                updateInputsVisibility();
            }
        });
        //dropdown menu array adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);
        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentActivitiesPosition = String.valueOf(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void checkInputs(){
        if (inMetricUnits) {
            if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0 || ageYearsEditText.length() ==0) {
                Toast.makeText(MainActivity.this, "Fill out the empty fields", Toast.LENGTH_SHORT).show();
            } else {
                double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                double bmi = BMICalcUtil.getInstance().calculateBMIMetric(heightInCms, weightInKgs);
                displayBMI(bmi);
            }
        } else {
            if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0 || ageYearsEditText.length() ==0) {
                Toast.makeText(MainActivity.this, "Fill out the empty fields", Toast.LENGTH_SHORT).show();
            } else {
                double heightFeet = Double.parseDouble(heightFtEditText.getText().toString());
                double heightInches = Double.parseDouble(heightInEditText.getText().toString());
                double weightLbs = Double.parseDouble(weightLbsEditText.getText().toString());
                double bmi = BMICalcUtil.getInstance().calculateBMIImperial(heightFeet, heightInches, weightLbs);
                displayBMI(bmi);
            }
        }
    }
    private void checkBMR(){
        if (inMetricUnits){
                double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                double age = Double.parseDouble(ageYearsEditText.getText().toString());
                double bmr = BMICalcUtil.getInstance().calculateBMRMetricMan(heightInCms, weightInKgs ,age);
                displayBMR(bmr);
        } else {
                double heightFeet = Double.parseDouble(heightFtEditText.getText().toString());
                double heightInches = Double.parseDouble(heightInEditText.getText().toString());
                double weightLbs = Double.parseDouble(weightLbsEditText.getText().toString());
                double age = Double.parseDouble(ageYearsEditText.getText().toString());
                double bmr = BMICalcUtil.getInstance().calculateBMRImperialMan(heightFeet, heightInches, weightLbs, age);
                displayBMR(bmr);
            }
        }





    private void updateInputsVisibility() {
        if (inMetricUnits) {
            heightCmEditText.setVisibility(View.VISIBLE);
            weightKgEditText.setVisibility(View.VISIBLE);
            heightFtEditText.setVisibility(View.GONE);
            heightInEditText.setVisibility(View.GONE);
            weightLbsEditText.setVisibility(View.GONE);
        } else {
            heightCmEditText.setVisibility(View.GONE);
            weightKgEditText.setVisibility(View.GONE);
            heightFtEditText.setVisibility(View.VISIBLE);
            heightInEditText.setVisibility(View.VISIBLE);
            weightLbsEditText.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("DefaultLocale")
    private void displayBMI(double bmi) {
        bmiResultCardView.setVisibility(View.VISIBLE);

        bmiTextView.setText(String.format("%.2f", bmi));

        String bmiCategory = BMICalcUtil.getInstance().classifyBMI(bmi);
        categoryTextView.setText(bmiCategory);

        switch (bmiCategory) {
            case BMICalcUtil.BMI_CATEGORY_UNDERWEIGHT:
                bmiResultCardView.setCardBackgroundColor(Color.CYAN);
                break;
            case BMICalcUtil.BMI_CATEGORY_HEALTHY:
                bmiResultCardView.setCardBackgroundColor(Color.GREEN);
                break;
            case BMICalcUtil.BMI_CATEGORY_OVERWEIGHT:
                bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                break;
            case BMICalcUtil.BMI_CATEGORY_OBESE:
                bmiResultCardView.setCardBackgroundColor(Color.RED);
                break;
        }

    }
    @SuppressLint("DefaultLocale")
    private void displayBMR (double bmr) {
        bmrResultCardView.setVisibility(View.VISIBLE);

        bmrTextView.setText(String.format("%.2f", bmr));

        String bmrCategory = BMICalcUtil.getInstance().classifyBMI(bmr);
        categoryTextView.setText(bmrCategory);

    }

    }