package commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;


public class BanCommand extends Command {

    public BanCommand() {
        this.name = "ban";
        this.arguments = "<user>";
        this.help = "Ban user from server";
        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
        this.botPermissions = new Permission[]{Permission.BAN_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        TextChannel channel = commandEvent.getTextChannel();
        StringBuilder message = new StringBuilder();
        boolean membersBanned = false;

        for (Member member : commandEvent.getMessage().getMentionedMembers()) {
            if (commandEvent.getMember().canInteract(member) && commandEvent.getSelfMember().canInteract(member)) {
                member.ban(0).queue(
                        success -> {
                            message.append(member.getEffectiveName()).append(" banned\n");
                        }, (t) -> {
                            message.append(member.getEffectiveName()).append(" not banned\n")
                                    .append(t.getMessage());
                        }
                );
                membersBanned = true;
            } else {
                message.append("Cannot ban ").append(member.getEffectiveName()).append(" with equal or higher role");
            }
        }

        if (!membersBanned && message.length() == 0) {
            message.append("No mentioned users to ban");
        }

        channel.sendMessage(message)
                .queue();
    }
}
