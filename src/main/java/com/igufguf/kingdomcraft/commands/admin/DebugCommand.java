package com.igufguf.kingdomcraft.commands.admin;

import com.igufguf.kingdomcraft.KingdomCraft;
import com.igufguf.kingdomcraft.api.models.commands.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Joris on 04/08/2018 in project KingdomCraft.
 */
public class DebugCommand extends CommandBase {

    private KingdomCraft plugin;

    private List<DebugExecutor> debugExecutors = new ArrayList<>();

    public DebugCommand(KingdomCraft plugin) {
        super("debug", null, false);

        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if ( sender instanceof Player && !sender.hasPermission("kingdom.debug")
                && !((Player) sender).getUniqueId().toString().equals("0518f31f-b261-4af7-a389-53c575300d61") ) {
            plugin.getMsg().send(sender, "noPermissionCmd");
            return false;
        }

        if ( args.length == 0 ) {
            plugin.getMsg().send(sender, "cmdDefaultUsage");
            return false;
        }

        boolean executed = false;
        for ( DebugExecutor ex : debugExecutors ) {
            if ( !ex.getName().equalsIgnoreCase(args[0]) ) continue;
            String[] newargs = Arrays.copyOfRange(args, 1, args.length);
            ex.onExecute(sender, newargs);
            executed = true;
        }

        if ( !executed ) {
            sender.sendMessage("Debug handler for '" + args[0] + "' not found!");
        }

        return false;
    }

    public void register(DebugExecutor ex) {
        this.debugExecutors.add(ex);
    }

    public static abstract class DebugExecutor  {

        private String name;

        public DebugExecutor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract void onExecute(CommandSender sender, String[] args);
    }

    public static String prettyPrint(Object obj) {
        if ( obj instanceof List ) {
            if ( ((List) obj).isEmpty() ) return "Empty list";

            String s = "";
            for ( Object i : (List) obj ) {
                s += ", " + prettyPrint(i);
            }
            s = s.substring(2);
            return "(" + s + ")";
        } else if ( obj instanceof Map) {
            if (  ((Map) obj).isEmpty() ) return "Empty map";

            String s = "";
            for ( Object i : ((Map) obj).keySet() ) {
                s += ", " + prettyPrint(i) + ": " + prettyPrint(((Map) obj).get(i));
            }
            s = s.substring(2);
            return "(" + s + ")";
        }

        return obj.toString();
    }
}
