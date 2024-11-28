package com.example.unitcon;

import android.widget.AdapterView;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerCategory, spinnerConversion;
    private TextInputEditText inputValue;
    private TextView outputValue;
    private MaterialButton convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Views
        spinnerCategory = findViewById(R.id.spinner_units);
        spinnerConversion = findViewById(R.id.spinner_conversion);
        inputValue = findViewById(R.id.input_value);
        outputValue = findViewById(R.id.output_value);
        convertButton = findViewById(R.id.convert_button);

        // Category options
        String[] categories = {"Length", "Volume", "Area", "Speed", "Weight", "Temperature"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Set default conversion options based on the first category (Length)
        updateConversionOptions(0);

        // Set listener for category spinner
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateConversionOptions(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        // Set listener for the convert button
        convertButton.setOnClickListener(v -> performConversion());
    }

    // Update the second spinner based on the selected category
    private void updateConversionOptions(int categoryPosition) {
        String[] conversions;

        switch (categoryPosition) {
            case 0: // Length
                conversions = new String[]{
                        "Meter to Feet", "Inch to Centimeter", "Kilometer to Mile",
                        "Feet to Meter", "Centimeter to Inch", "Mile to Kilometer"
                };
                break;
            case 1: // Volume
                conversions = new String[]{
                        "Liter to Cubic Meter", "Liter to Gallon", "Cubic Meter to Liter", "Gallon to Liter"
                };
                break;
            case 2: // Area
                conversions = new String[]{
                        "Square Meter to Square Yard", "Square Meter to Hectare", "Square Meter to Square Mile",
                        "Square Yard to Square Meter", "Hectare to Square Meter", "Square Mile to Square Meter"
                };
                break;
            case 3: // Speed
                conversions = new String[]{
                        "Meter per Second to Kilometer per Hour", "Kilometer per Hour to Mile per Hour",
                        "Meter per Second to Mile per Hour", "Kilometer per Hour to Meter per Second",
                        "Mile per Hour to Kilometer per Hour", "Mile per Hour to Meter per Second"
                };
                break;
            case 4: // Weight
                conversions = new String[]{
                        "Gram to Kilogram", "Kilogram to Pound", "Kilogram to Ounce",
                        "Kilogram to Gram", "Pound to Kilogram", "Ounce to Kilogram"
                };
                break;
            case 5: // Temperature
                conversions = new String[]{
                        "Celsius to Fahrenheit", "Celsius to Kelvin", "Fahrenheit to Kelvin",
                        "Fahrenheit to Celsius", "Kelvin to Celsius", "Kelvin to Fahrenheit"
                };
                break;
            default:
                conversions = new String[]{};
        }

        ArrayAdapter<String> conversionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, conversions);
        conversionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConversion.setAdapter(conversionAdapter);
    }

    // Perform the conversion based on selected category and unit
    private void performConversion() {
        String category = spinnerCategory.getSelectedItem().toString();
        String conversion = spinnerConversion.getSelectedItem().toString();
        String inputValueStr = inputValue.getText().toString();

        if (inputValueStr.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(inputValueStr);
        double result = 0;

        try {
            result = performConversionLogic(category, conversion, value);
            outputValue.setText("Converted Value: " + result);
        } catch (Exception e) {
            Toast.makeText(this, "Error in conversion", Toast.LENGTH_SHORT).show();
        }
    }

    // Perform the conversion logic based on category and conversion type
    private double performConversionLogic(String category, String conversion, double value) {
        switch (category) {
            case "Length":
                switch (conversion) {
                    case "Meter to Feet":
                        return value * 3.28084;
                    case "Inch to Centimeter":
                        return value * 2.54;
                    case "Kilometer to Mile":
                        return value * 0.621371;
                    case "Feet to Meter":
                        return value / 3.28084;
                    case "Centimeter to Inch":
                        return value / 2.54;
                    case "Mile to Kilometer":
                        return value / 0.621371;
                }
                break;
            case "Volume":
                switch (conversion) {
                    case "Liter to Cubic Meter":
                        return value / 1000;
                    case "Liter to Gallon":
                        return value * 0.264172;
                    case "Cubic Meter to Liter":
                        return value * 1000;
                    case "Gallon to Liter":
                        return value / 0.264172;
                }
                break;
            case "Area":
                switch (conversion) {
                    case "Square Meter to Square Yard":
                        return value * 1.19599;
                    case "Square Meter to Hectare":
                        return value / 10000;
                    case "Square Meter to Square Mile":
                        return value / 2.58999e6;
                    case "Square Yard to Square Meter":
                        return value / 1.19599;
                    case "Hectare to Square Meter":
                        return value * 10000;
                    case "Square Mile to Square Meter":
                        return value * 2.58999e6;
                }
                break;
            case "Speed":
                switch (conversion) {
                    case "Meter per Second to Kilometer per Hour":
                        return value * 3.6;
                    case "Kilometer per Hour to Mile per Hour":
                        return value * 0.621371;
                    case "Meter per Second to Mile per Hour":
                        return value * 2.23694;
                    case "Kilometer per Hour to Meter per Second":
                        return value / 3.6;
                    case "Mile per Hour to Kilometer per Hour":
                        return value / 0.621371;
                    case "Mile per Hour to Meter per Second":
                        return value / 2.23694;
                }
                break;
            case "Weight":
                switch (conversion) {
                    case "Gram to Kilogram":
                        return value / 1000;
                    case "Kilogram to Pound":
                        return value * 2.20462;
                    case "Kilogram to Ounce":
                        return value * 35.274;
                    case "Kilogram to Gram":
                        return value * 1000;
                    case "Pound to Kilogram":
                        return value / 2.20462;
                    case "Ounce to Kilogram":
                        return value / 35.274;
                }
                break;
            case "Temperature":
                switch (conversion) {
                    case "Celsius to Fahrenheit":
                        return (value * 9/5) + 32;
                    case "Celsius to Kelvin":
                        return value + 273.15;
                    case "Fahrenheit to Kelvin":
                        return (value - 32) * 5/9 + 273.15;
                    case "Fahrenheit to Celsius":
                        return (value - 32) * 5/9;
                    case "Kelvin to Celsius":
                        return value - 273.15;
                    case "Kelvin to Fahrenheit":
                        return (value - 273.15) * 9/5 + 32;
                }
                break;
        }
        return value;
    }
}
