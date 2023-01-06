package dev.minecode.core.common;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CloudPlattform;
import dev.minecode.core.common.api.CoreAPIProvider;
import dev.minecode.core.common.api.cloudsupport.SimpleCloudPluginMessage;
import dev.minecode.core.common.util.UUIDFetcher;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.message.IMessageChannel;
import org.jetbrains.annotations.Nullable;

public class CoreCommon {
    private static CoreCommon instance;

    private UUIDFetcher uuidFetcher;
    private IMessageChannel<SimpleCloudPluginMessage> messageChannel;

    public CoreCommon() {
        makeInstances();
    }

    public static CoreCommon getInstance() {
        if (instance == null) new CoreCommon();
        return instance;
    }

    private void makeInstances() {
        instance = this;
        uuidFetcher = new UUIDFetcher();
        new CoreAPIProvider();

        if (CoreAPI.getInstance().getNetworkManager().getCloudPlattform() == CloudPlattform.SIMPLECLOUD) {
            messageChannel = CloudAPI.getInstance().getMessageChannelManager().registerMessageChannel(
                    CloudAPI.getInstance().getThisSidesCloudModule(), "minecode:pluginmessage", SimpleCloudPluginMessage.class);
        }
    }

    public UUIDFetcher getUuidFetcher() {
        return uuidFetcher;
    }

    @Nullable
    public IMessageChannel<SimpleCloudPluginMessage> getMessageChannel() {
        return messageChannel;
    }
}
