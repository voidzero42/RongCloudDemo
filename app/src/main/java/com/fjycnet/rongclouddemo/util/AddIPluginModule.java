package com.woai.thirdsdk.rong;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by McGrady on 2017/2/27.
 */

public class AddIPluginModule extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        super.getPluginModules(conversationType);
        List<IPluginModule> list = super.getPluginModules(conversationType);
        IPluginModule temp = null;
        for (IPluginModule module : list) {
            if (module instanceof FilePlugin) {
                temp = module;
                break;
            }
        }
        list.remove(temp);
        return list;
    }


    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return super.getEmoticonTabs();
    }
}
