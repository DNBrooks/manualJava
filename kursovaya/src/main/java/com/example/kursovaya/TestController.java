package com.example.kursovaya;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button next_id;

    @FXML
    private Text question_text;

    @FXML
    private Button stop_id;

    @FXML
    private RadioButton radio1;

    @FXML
    private ToggleGroup answers;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    @FXML
    private RadioButton radio4;

    private Questions[] questions = new Questions[] {
            new Questions("Что такое адресация?", new String[] {"название данное листу Excel", "адрес компьютера, на котором создан документ", "указание куда поместить результат", "указание на данные находящиеся в определенной ячейке"}),
            new Questions("Что такое относительная адресация ?", new String[] {"одинаковая адресация для всех ячеек", "так называется обычная адресация", "когда идет указание на конкретную ячейку Excel", "адресация относительно текущего положения ячейки"}),
            new Questions("Какие способы передачи используются в современных компьютерных сетях?", new String[] {"коммутация сообщений", "коммутация писем", "коммутация каналов", "коммутация пакетов"}),
            new Questions("Какая стратегия маршрутизации обеспечивает эффективную загрузку сети?", new String[] {"изолированная", "распределенная", "централизованная", "смешанная"}),
            new Questions("Строку таблицы реляционной базы данных называют?", new String[] {"ячейкой", "полем", "графом", "записью"}),
            new Questions("Стоблец таблицы реляционный базы данных называю?", new String[] {"записью", "разделом", "графом", "полем"}),
            new Questions("В записи файла реляционной базы данных может содержаться:", new String[] {"только текстовая информация", "только логические величин", "исключительно числовая информация", "данные разных типов"}),
            new Questions("Структура файла реляционной базы данным меняется:", new String[] {"при изменении любой записи", "при уничтожении всех записей", "при добавлении одной или нескольких записей", "при удалении любого поля"})
    };
    // Переменные для установки текущего номера вопроса и для подсчета количества верных ответов
    private int nowQuestion = 0, correctAnswers;
    // В эту переменную будет устанавливаться корректный ответ текущего вопроса
    private String nowCorrectAnswer;

    @FXML
    void initialize() {


        nowCorrectAnswer = questions[nowQuestion].correctAnswer();
        next_id.setOnAction(actionEvent -> {

           RadioButton selectedRadio = (RadioButton) answers.getSelectedToggle();
            if(selectedRadio != null) {
                // Получаем текст ответа
                String toogleGroupValue = selectedRadio.getText();

                // Сверяем ответ с корректным
                if(toogleGroupValue.equals(nowCorrectAnswer)) {
                    // Выводим информацию и увеличиваем количество верных ответов
                    System.out.println("Верный ответ");
                    correctAnswers++;
                } else
                    System.out.println("Не верный ответ");

                // Если сейчас был последний вопрос, то скрываем все поля
                if(nowQuestion + 1 == questions.length) {
                    radio1.setVisible(false); // Скрываем все поля для выбора
                    radio2.setVisible(false);
                    radio3.setVisible(false);
                    radio4.setVisible(false);
                    next_id.setVisible(false); // Скрываем кнопку ответа

                    // Показываем текст в конце
                    correctAnswers++;
                    question_text.setText("Вы ответили корректно на " + correctAnswers + " из " + questions.length  + " вопросов!");
                } else { // Если еще есть вопросы...
                    // Увеличиваем номер текущего вопроса
                    nowQuestion++;
                    // Указываем новый текст верного ответа
                    nowCorrectAnswer = questions[nowQuestion].correctAnswer();

                    // Меняем текст вопроса в программе
                    question_text.setText(questions[nowQuestion].getQuestion());
                    // Получаем массив ответов
                    String[] answers = questions[nowQuestion].getAnswers();

                    // Преобразовываем в список (так удобнее сортировать элементы)
                    List<String> intList = Arrays.asList(answers);

                    // Сортируем в случайном порядке
                    Collections.shuffle(intList);

                    // Подставляем ответы в радио кнопки
                    radio1.setText(intList.get(0));
                    radio2.setText(intList.get(1));
                    radio3.setText(intList.get(2));
                    radio4.setText(intList.get(3));

                    // Снимаем выделение, что указал пользователь ранее
                    selectedRadio.setSelected(false);
                }

            }
       });

        stop_id.setOnAction(actionEvent -> {
            openNewScene("hello-view.fxml");
        });
    }

    public void openNewScene(String window) {
        stop_id.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));


        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent par = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Электронное учебное пособие");
        stage.setResizable(false);
        stage.setScene(new Scene(par));
        stage.show();
    }
}
