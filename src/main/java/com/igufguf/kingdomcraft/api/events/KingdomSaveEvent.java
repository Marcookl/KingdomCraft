package com.igufguf.kingdomcraft.api.events;

import com.igufguf.kingdomcraft.api.models.kingdom.Kingdom;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Joris on 19/08/2018 in project KingdomCraft.
 */
public class KingdomSaveEvent extends KingdomEvent {

    private Kingdom kingdom;
    private ConfigurationSection configurationSection;

    public KingdomSaveEvent(Kingdom kingdom, ConfigurationSection configurationSection) {
        this.kingdom = kingdom;
        this.configurationSection = configurationSection;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public ConfigurationSection getConfigurationSection() {
        return configurationSection;
    }
}
