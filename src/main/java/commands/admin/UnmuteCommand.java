package commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.TextChannel;

public class UnmuteCommand extends Command {

    public UnmuteCommand() {
        this.name = "unmute";
        this.arguments = "<user>";
        this.help = "Unmute user in server";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        TextChannel channel = commandEvent.getTextChannel();
    }
}
