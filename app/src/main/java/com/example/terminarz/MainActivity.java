package com.example.terminarz;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnPickDateTime, btnPickTime;
    private TextView tvSelectedDateTime;

    // Zmienne do przechowywania wyboru użytkownika

    LocalDateTime currentDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPickDateTime = findViewById(R.id.btnPickDateTime);
        tvSelectedDateTime = findViewById(R.id.tvSelectedDateTime);
        btnPickTime = findViewById(R.id.btnPickTime);
        currentDate = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());

        btnPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        btnPickDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }

    // 1. Metoda otwierająca Kalendarz
    private void showDatePicker() {
        // Pobieramy aktualną datę, żeby ustawić ją jako domyślną w dialogu
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Zapisujemy wybraną datę do zmiennych


                        // Po wybraniu daty, automatycznie otwieramy zegar
                        currentDate = LocalDateTime.of(year, monthOfYear +1, dayOfMonth, currentDate.getHour(), currentDate.getMinute());
                        if (currentDate.isBefore(LocalDateTime.now())) {
                            Toast.makeText(MainActivity.this, "Nie można cofnąć czasu!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Formatujemy i wyświetlamy
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
                            tvSelectedDateTime.setText("Umówiono: " + currentDate.format(formatter));
                        }

                    }
                },
                year, month, day // Wartości domyślne (startowe)
        );

        datePickerDialog.show();
    }

    // 2. Metoda otwierająca Zegar
    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Tutaj mamy już komplet danych: Rok, Miesiąc, Dzień, Godzinę i Minutę

                        // Tworzymy obiekt LocalDateTime (miesiąc + 1 dla LocalDate!)
                        currentDate = LocalDateTime.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth(), hourOfDay, minute);

                        if (currentDate.isBefore(LocalDateTime.now())) {
                            Toast.makeText(MainActivity.this, "Nie można cofnąć czasu!", Toast.LENGTH_SHORT).show();
                        } else {
                            // 5. Formatowanie wyświetlania
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
                            tvSelectedDateTime.setText("Umówiono: " + currentDate.format(formatter));
                        }
                        // Formatujemy i wyświetlamy

                    }
                },
                hour, minute, true // true = format 24h
        );

        timePickerDialog.show();
    }

}