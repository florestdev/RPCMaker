package ru.florestdev;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityButton;
import de.jcm.discordgamesdk.activity.ActivityType;

import java.util.function.Consumer;

public class Worker {
    private static Core core;

    public static void run(String details, String state,
                           String button1Label, String button1Url,
                           String button2Label, String button2Url) {

        // 1. Создаём ядро Discord Game SDK
        try (CreateParams params = new CreateParams()) {
            params.setClientID(1559553727713779712L);
            params.setFlags(CreateParams.getDefaultFlags());

            core = new Core(params);

            // 2. Создаём активность
            try (Activity activity = new Activity()) {
                activity.setDetails(details);
                activity.setState(state);
                activity.setType(ActivityType.PLAYING);

                // Таймер "играет с..."
                activity.timestamps().setStart(java.time.Instant.now());

                // Кнопки (максимум 2)
                if (button1Label != null && button1Url != null) {
                    activity.addButton(new ActivityButton(button1Label, button1Url));
                }
                if (button2Label != null && button2Url != null) {
                    activity.addButton(new ActivityButton(button2Label, button2Url));
                }

                // 3. Отправляем в Discord
                core.activityManager().updateActivity(activity);
            }

            // 4. Держим живым и обрабатываем колбэки
            while (!Thread.currentThread().isInterrupted()) {
                core.runCallbacks();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (core != null) core.close();
        }
    }
}