package com.ladalada.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Core {

    private VkApiClient apiClient;
    private static int ts;
    private GroupActor groupActor;
    private static int maxMsgId = -1;

    public Core() throws Exception {

        apiClient = new VkApiClient(HttpTransportClient.getInstance());

        Properties properties = new Properties();
        int groupId;
        String access_token;

        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            groupId = Integer.valueOf(properties.getProperty("groupId"));
            access_token = properties.getProperty("accessToken");

            groupActor = new GroupActor(groupId, access_token);
            ts = apiClient.messages().getLongPollServer(groupActor).execute().getTs();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при загрузке файла конфигурации.");
        }

    }

    public GroupActor getGroupActor() {
        return groupActor;
    }

    public VkApiClient getApiClient() {
        return apiClient;
    }

    public Message getMessage() throws Exception {

        MessagesGetLongPollHistoryQuery eventsQuery = apiClient.messages()
                .getLongPollHistory(groupActor)
                .ts(ts);

        if (maxMsgId > 0){
            eventsQuery.maxMsgId(maxMsgId);
        }

        List<Message> messages = eventsQuery
                .execute()
                .getMessages()
                .getMessages();

        if (!messages.isEmpty()){
            try {
                ts =  apiClient.messages()
                        .getLongPollServer(groupActor)
                        .execute()
                        .getTs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!messages.isEmpty() && !messages.get(0).isOut()) {
            int messageId = messages.get(0).getId();
            if (messageId > maxMsgId){
                maxMsgId = messageId;
            }
            return messages.get(0);
        }

        return null;
    }
}
