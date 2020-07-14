package com.ladalada.vk;

public class Sender {

    public static Core core;

    static {
        try {
            core = new Core();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg, int peerId){
        if (msg == null){
            System.out.println("null");
            return;
        }
        try {
            core.getApiClient().messages().send(core.getGroupActor()).peerId(peerId).message(msg).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
