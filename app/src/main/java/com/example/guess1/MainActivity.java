package com.example.guess1;
import com.example.guess1.R;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int randomNumber;
    private int attempts;
    private final int maxAttempts = 5; // Максимальное количество попыток
    private TextView hintTextView;
    private EditText guessEditText;
    private Button guessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        hintTextView = findViewById(R.id.hintTextView);
        guessEditText = findViewById(R.id.guessEditText);
        guessButton = findViewById(R.id.guessButton);

        // Генерация случайного числа от 0 до 20
        randomNumber = new Random().nextInt(21);
        System.out.println("Загаданное число: " + randomNumber); // Отображение числа в терминале

        attempts = 0;

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userGuessString = guessEditText.getText().toString();

                // Проверка на пустое поле
                if (!userGuessString.isEmpty()) {
                    try {
                        int userGuess = Integer.parseInt(userGuessString);

                        // Проверка, что число в диапазоне от 0 до 20
                        if (userGuess < 0 || userGuess > 20) {
                            hintTextView.setText("Число должно быть в диапазоне от 0 до 20.");
                        } else {
                            attempts++;

                            // Проверка количества попыток
                            if (attempts <= maxAttempts) {
                                System.out.println("Попытка #" + attempts + ": введённое число — " + userGuess);

                                // Подсказки в зависимости от расстояния до загаданного числа
                                if (userGuess < randomNumber) {
                                    int diff = randomNumber - userGuess;
                                    if (diff > 10) {
                                        hintTextView.setText("Слишком маленькое число! Вы очень далеки.");
                                    } else if (diff > 5) {
                                        hintTextView.setText("Меньше! Немного далеко.");
                                    } else {
                                        hintTextView.setText("Меньше! Очень близко.");
                                    }
                                } else if (userGuess > randomNumber) {
                                    int diff = userGuess - randomNumber;
                                    if (diff > 10) {
                                        hintTextView.setText("Слишком большое число! Вы очень далеки.");
                                    } else if (diff > 5) {
                                        hintTextView.setText("Больше! Немного далеко.");
                                    } else {
                                        hintTextView.setText("Больше! Очень близко.");
                                    }
                                } else {
                                    hintTextView.setText("Поздравляем! Вы угадали число " + randomNumber + " за " + attempts + " попыток.");
                                    guessButton.setEnabled(false); // Блокировка кнопки после выигрыша
                                }

                                // Если пользователь использовал все попытки и не угадал
                                if (attempts == maxAttempts && userGuess != randomNumber) {
                                    hintTextView.setText("Вы исчерпали все попытки! Загаданное число было " + randomNumber + ".");
                                    guessButton.setEnabled(false); // Блокировка кнопки после 5 попыток
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Проверка типизации: если введено не число
                        Toast.makeText(MainActivity.this, "Пожалуйста, введите целое число!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Введите число!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
