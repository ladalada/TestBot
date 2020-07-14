package com.ladalada;

import com.ladalada.vk.Core;
import com.ladalada.vk.Messenger;
import com.vk.api.sdk.objects.messages.Message;

import java.util.concurrent.Executors;

public class Server {

    public static Core core;

    static {
        try {
            core = new Core();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NullPointerException, InterruptedException {

        System.out.println("Сервер запущен.");

        while (true) {

            Thread.sleep(200);

            try {
                Message message = core.getMessage();
                if (message != null) {
                    Executors.newCachedThreadPool().execute(new Messenger(message));
                }
            } catch (Exception e) {
                System.out.println("Возникли проблемы. Повторное соединение.");
                final int RECONNECT_TIME = 10000;
                Thread.sleep(RECONNECT_TIME);
            }

        }
    }
}
