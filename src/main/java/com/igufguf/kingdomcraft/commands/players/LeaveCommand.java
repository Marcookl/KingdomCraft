package com.igufguf.kingdomcraft.commands.players;

import com.igufguf.kingdomcraft.api.models.commands.CommandBase;
import com.igufguf.kingdomcraft.api.models.kingdom.Kingdom;
import com.igufguf.kingdomcraft.KingdomCraft;
import com.igufguf.kingdomcraft.api.models.kingdom.KingdomUser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyrighted 2018 iGufGuf
 *
 * This file is part of KingdomCraft.
 *
 * Kingdomcraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * KingdomCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with KingdomCraft.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
public class LeaveCommand extends CommandBase {

	private final KingdomCraft plugin;

	public LeaveCommand(KingdomCraft plugin) {
		super("leave", "kingdom.leave", true);

		this.plugin = plugin;
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		
		if ( args.length != 0 ) {
			plugin.getMsg().send(sender, "cmdDefaultUsage");
			return false;
		}

		KingdomUser user = plugin.getApi().getUserHandler().getUser(p);
		Kingdom kingdom = plugin.getApi().getUserHandler().getKingdom(user);

		if ( kingdom == null ) {
			plugin.getMsg().send(sender, "cmdDefaultSenderNoKingdom");
			return false;
		}

		plugin.getApi().getUserHandler().setKingdom(user, null);

		plugin.getMsg().send(sender, "cmdLeaveSuccess", kingdom.getName());

		for ( Player member : plugin.getApi().getKingdomHandler().getOnlineMembers(kingdom) ) {
			plugin.getMsg().send(member, "cmdLeaveSuccessMembers", p.getName());
		}

		if ( plugin.getCfg().has("spawn-on-kingdom-leave") && plugin.getCfg().getBoolean("spawn-on-kingdom-leave") ) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "spawn " + p.getName());
		}
		return false;
	}
	
}
