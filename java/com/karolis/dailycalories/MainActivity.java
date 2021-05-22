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

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private EditText weightKgEditText, heightCmEditText, ageYearsEditText;
    private EditText weightLbsEditText, heightFtEditText, heightInEditText;
    private Button calculateButton;
    private TextView bmiTextView, categoryTextView, bmrTextView, activityBmrCategory;
    private ToggleButton toggleUnitsButton;
    private CardView bmiResultCardView, bmrResultCardView;
    private Spinner dropDownMenu, dropDownMenuGoals;
    private RadioButton radioMan, radioWoman;
    private RadioGroup radioGroup;
    private String genderSelected = "female";
    private int activitySelectionLevel;
    private int activityGoalsSelection;
    public boolean calculatesBmrByChoosingActivity = false;

    private boolean inMetricUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dropDownMenu = findViewById(R.id.dropdown_menu1);
        dropDownMenuGoals = findViewById(R.id.dropdown_menu2);

        weightKgEditText = findViewById(R.id.activity_main_weightkgs);
        heightCmEditText = findViewById(R.id.activity_main_heightcm);

        weightLbsEditText = findViewById(R.id.activity_main_weightlbs);
        heightFtEditText = findViewById(R.id.activity_main_heightfeet);
        heightInEditText = findViewById(R.id.activity_main_heightinches);

        calculateButton = findViewById(R.id.activity_main_calculate);
        toggleUnitsButton = findViewById(R.id.activity_main_toggleunits);

        ageYearsEditText = findViewById(R.id.yearsET);
        radioWoman = findViewById(R.id.radio_Woman1);
        radioMan = findViewById(R.id.radio_Man1);

        radioGroup = findViewById(R.id.radioGroup1);

        bmiTextView = findViewById(R.id.activity_main_bmi);
        categoryTextView = findViewById(R.id.activity_main_category);
        bmrTextView = findViewById(R.id.activity_main_bmr);
        activityBmrCategory = findViewById(R.id.activity_bmr_category);

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
                calculatesBmrByChoosingActivity = true;
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
//        dropdown menu array adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);
        dropDownMenu.setSelection(0);
        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ;
                activitySelectionLevel = position;
                if (calculatesBmrByChoosingActivity) {
                    checkBMR();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterGain = ArrayAdapter.createFromResource(this,
                R.array.Goals, android.R.layout.simple_spinner_item);
        adapterGain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenuGoals.setAdapter(adapterGain);
        dropDownMenuGoals.setSelection(0);
        dropDownMenuGoals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityGoalsSelection = position;
                checkBMR();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radio_Man1:
                        // switch to fragment 1
                        genderSelected = "male";

                        break;
                    case R.id.radio_Woman1:
                        genderSelected = "female";

                        // Fragment 2
                        break;
                }
            }
        });


    }

    private void checkInputs() {
        if (inMetricUnits) {
            if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0 || ageYearsEditText.length() == 0) {
                Toast.makeText(MainActivity.this, "Fill out the empty fields", Toast.LENGTH_SHORT).show();
            } else {
                double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                double bmi = BMICalcUtil.getInstance().calculateBMIMetric(heightInCms, weightInKgs);
                displayBMI(bmi);
            }
        } else {
            if (weightLbsEditText.length() == 0 || heightInEditText.length() == 0 ||
                    heightFtEditText.length() == 0 || ageYearsEditText.length() == 0) {
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

    private void checkBMR() {

        double bmr;

        ArrayList<Double> energyLevelValues = new ArrayList<>();
        energyLevelValues.add(1.0);
        energyLevelValues.add(1.2);
        energyLevelValues.add(1.375);
        energyLevelValues.add(1.55);
        energyLevelValues.add(1.725);
        energyLevelValues.add(1.9);

        if (inMetricUnits) {
            if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0 || ageYearsEditText.length() == 0) {
                Toast.makeText(MainActivity.this, "Fill out the empty fields", Toast.LENGTH_SHORT).show();
            } else {
                double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                double age = Double.parseDouble(ageYearsEditText.getText().toString());
                if (genderSelected.equals("male")) {
                    bmr = BMICalcUtil.getInstance().calculateBMRMetricMan(heightInCms, weightInKgs, age)
                            * energyLevelValues.get(activitySelectionLevel) ;

                } else{
                    bmr = BMICalcUtil.getInstance().calculateBMRMetricWoman(heightInCms, weightInKgs, age)
                            * energyLevelValues.get(activitySelectionLevel);
                }
                displayBMR(bmr);
            }
        } else {
            if (weightLbsEditText.length() == 0 || heightInEditText.length() == 0 ||
                    heightFtEditText.length() == 0 || ageYearsEditText.length() == 0) {
                Toast.makeText(MainActivity.this, "Fill out the empty fields", Toast.LENGTH_SHORT).show();
            } else {
                double heightFeet = Double.parseDouble(heightFtEditText.getText().toString());
                double heightInches = Double.parseDouble(heightInEditText.getText().toString());
                double weightLbs = Double.parseDouble(weightLbsEditText.getText().toString());
                double age = Double.parseDouble(ageYearsEditText.getText().toString());

                if (genderSelected.equals("male")) {
                    bmr = BMICalcUtil.getInstance().calculateBMRImperialMan(heightFeet, heightInches, weightLbs, age) * energyLevelValues.get(activitySelectionLevel);

                } else {
                    bmr = BMICalcUtil.getInstance().calculateBMRImperialWoman(heightFeet, heightInches, weightLbs, age) * energyLevelValues.get(activitySelectionLevel);
                }

                displayBMR(bmr);
            }}
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

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void displayBMR(double bmr) {

        ArrayList<Double> goalsList = new ArrayList<>();

        goalsList.add(0d);
        goalsList.add(-300.0);
        goalsList.add(-500.0);
        goalsList.add(0.15 * bmr);
        goalsList.add(0.2 * bmr);


        bmrResultCardView.setVisibility(View.VISIBLE);

        bmrTextView.setText(String.format("Your BMR is: " +"%.2f", bmr ) + " - this is the amount of calories you should consume to maintain your weight");
        bmrResultCardView.setCardBackgroundColor(bmiResultCardView.getCardBackgroundColor());
        if (activityGoalsSelection == 0){
            activityBmrCategory.setText(String.format("Please select your goal to find out more."));
        } if (activityGoalsSelection == 1){
            activityBmrCategory.setText(String.format("In order to lose weight you have to consume: "
                    + "%.2f", bmr + goalsList.get(activityGoalsSelection)) + "Calories");
        }   if (activityGoalsSelection == 2){
            activityBmrCategory.setText(String.format("In order to lose weight quicker you have to consume: "
                    + "%.2f", bmr + goalsList.get(activityGoalsSelection)) + "Calories");
        }   if (activityGoalsSelection == 3){
            activityBmrCategory.setText(String.format("In order to gain weight you have to consume: "
                    + "%.2f", bmr + goalsList.get(activityGoalsSelection)) + "Calories");
        }   if(activityGoalsSelection == 4){
            activityBmrCategory.setText(String.format("In order to gain weight quicker you have to consume: "
                    + "%.2f", bmr + goalsList.get(activityGoalsSelection)) + "Calories");
        }
    }


}
