package dev.minecode.core.common.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.object.PluginMessage;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PluginMessageManager {

    private ArrayList<PluginMessage> pluginMessageQueue;

    public PluginMessageManager() {
        pluginMessageQueue = new ArrayList<>();
    }

    public void runSQLChecker() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Statement statement = CoreAPI.getInstance().getDatabaseManager().getStatement();

        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM minecode_messaging WHERE RECEIVERNAME = '" + CoreAPI.getInstance().getProcessName() + "'");
                    while (resultSet.next()) {
                        CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(resultSet.getString("CHANNEL"),
                                resultSet.getString("SUBCHANNEL"),
                                resultSet.getString("SENDERNAME"),
                                Type.valueOf(resultSet.getString("SENDERTYPE")),
                                resultSet.getString("RECEIVERNAME"),
                                CoreCommon.getInstance().getPluginMessageManager().getMessageHashMap(resultSet.getString("MESSAGE")));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        // Make the delay adjustable
        },1, TimeUnit.MILLISECONDS);
    }

    public ByteArrayOutputStream getByteArrayOutputStream(String channel, String subChannel, String senderName, Type senderType, String receiverName, HashMap<String, Object> message) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);
        ObjectOutputStream objectOutput;

        try {
            dataOutput.writeUTF(channel);
            dataOutput.writeUTF(subChannel);
            dataOutput.writeUTF(senderName);
            dataOutput.writeUTF(senderType.toString());
            dataOutput.writeUTF(receiverName);
            objectOutput = new ObjectOutputStream(dataOutput);
            objectOutput.writeObject(message);
            objectOutput.flush();
            objectOutput.close();
            dataOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutput;
    }

    /*
    public ByteArrayInputStream getByteArrayInputStream(byte[] bytes) {
        ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(bytes);
        DataInputStream dataInput = new DataInputStream(byteArrayInput);

        String channel;
        String subChannel;
        String senderName;
        Type senderType;
        String receiverName;
        HashMap<String, Object> message;
        ObjectInputStream objectInput;

        try {
            channel = dataInput.readUTF();
            subChannel = dataInput.readUTF();
            senderName = dataInput.readUTF();
            senderType = Type.valueOf(dataInput.readUTF());
            receiverName = dataInput.readUTF();
            objectInput = new ObjectInputStream(dataInput);
            message = (HashMap<String, Object>) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return byteArrayInput;
    }
     */

    public HashMap<String, Object> getMessageHashMap(ByteArrayInputStream byteArrayInput) {
        HashMap<String, Object> message = null;
        ObjectInputStream objectInput;

        try {
            objectInput = new ObjectInputStream(byteArrayInput);
            message = (HashMap<String, Object>) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public HashMap<String, Object> getMessageHashMap(String data) {
        return getMessageHashMap(new ByteArrayInputStream(data.getBytes()));
    }

    public ArrayList<PluginMessage> getPluginMessageQueue() {
        return pluginMessageQueue;
    }
}
