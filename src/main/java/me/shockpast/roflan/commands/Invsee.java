package me.shockpast.roflan.commands;

import me.shockpast.roflan.constants.Colors;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Invsee implements CommandExecutor {
    public Invsee() {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String line, @NotNull String[] args) {
        if (args.length < 1)
            return false;

        Player player = (Player)sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Component.text(args[0], Colors.Blue)
                    .append(Component.text(" doesn't exist on server.", Colors.White)));

            return true;
        }

        player.getInventory().close();
        player.openInventory(target.getInventory())
                .setTitle(target.getName());

        return true;
    }
}
