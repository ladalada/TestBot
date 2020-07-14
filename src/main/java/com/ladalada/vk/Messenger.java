package com.ladalada.vk;

import com.vk.api.sdk.objects.messages.Message;

public class Messenger implements Runnable {

    private Message message;

    public Messenger(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        new Sender().sendMessage("Вы сказали: " + message.getBody(), message.getUserId());
    }

}
