package com.nepxion.polaris.guide.service.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public class CoreImpl {
    @Autowired
    private PluginAdapter pluginAdapter;

    public String getPluginInfo(String value) {
        return pluginAdapter.getPluginInfo(value);
    }
}