package ru.florestdev;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // Устанавливаем Look and Feel системы (чтобы окно выглядело нативно)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Запускаем GUI в потоке обработки событий Swing
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("RPC Maker от FlorestDev");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null); // Центрируем окно
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Заголовок ---
        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(new JLabel("Заголовок (Details):"), gbc);
        gbc.gridy = 1;
        JTextField detailsField = new JTextField();
        frame.add(detailsField, gbc);

        // --- Детали ---
        gbc.gridy = 2;
        frame.add(new JLabel("Детали (State):"), gbc);
        gbc.gridy = 3;
        JTextField stateField = new JTextField();
        frame.add(stateField, gbc);

        // --- Кнопка 1 ---
        gbc.gridy = 4;
        frame.add(new JLabel("Заголовок кнопки 1 (оставьте пустым, если кнопок нет):"), gbc);
        gbc.gridy = 5;
        JTextField btn1NameField = new JTextField();
        frame.add(btn1NameField, gbc);

        gbc.gridy = 6;
        frame.add(new JLabel("Ссылка кнопки 1:"), gbc);
        gbc.gridy = 7;
        JTextField btn1UrlField = new JTextField();
        frame.add(btn1UrlField, gbc);

        // --- Кнопка 2 ---
        gbc.gridy = 8;
        frame.add(new JLabel("Заголовок кнопки 2:"), gbc);
        gbc.gridy = 9;
        JTextField btn2NameField = new JTextField();
        frame.add(btn2NameField, gbc);

        gbc.gridy = 10;
        frame.add(new JLabel("Ссылка кнопки 2:"), gbc);
        gbc.gridy = 11;
        JTextField btn2UrlField = new JTextField();
        frame.add(btn2UrlField, gbc);

        // --- Кнопка запуска ---
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 10, 10);
        JButton startButton = new JButton("Ставлю активность...");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(startButton, gbc);

        // --- Обработчик нажатия ---
        startButton.addActionListener(e -> {
            String details = detailsField.getText().trim();
            String state = stateField.getText().trim();
            String btn1Name = btn1NameField.getText().trim();
            String btn1Url = btn1UrlField.getText().trim();
            String btn2Name = btn2NameField.getText().trim();
            String btn2Url = btn2UrlField.getText().trim();

            // Логика: если заголовок кнопки 1 пустой — кнопок нет
            if (btn1Name.isEmpty()) {
                btn1Name = null;
                btn1Url = null;
                btn2Name = null;
                btn2Url = null;
            }

            // Валидация: заголовок обязателен
            if (details.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Поле 'Заголовок' не может быть пустым!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Блокируем кнопку, чтобы не нажали дважды
            startButton.setEnabled(false);
            startButton.setText("Работаю...");

            // Запускаем Worker в отдельном потоке, чтобы не вешать GUI
            final String fDetails = details;
            final String fState = state;
            final String fBtn1Name = btn1Name;
            final String fBtn1Url = btn1Url;
            final String fBtn2Name = btn2Name;
            final String fBtn2Url = btn2Url;

            new Thread(() -> {
                try {
                    Worker.run(fDetails, fState, fBtn1Name, fBtn1Url, fBtn2Name, fBtn2Url);
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(frame, "Активность успешно установлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                        startButton.setEnabled(true);
                        startButton.setText("Ставлю активность...");
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(frame, "Ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                        startButton.setEnabled(true);
                        startButton.setText("Ставлю активность...");
                    });
                }
            }).start();
        });

        frame.setVisible(true);
    }
}