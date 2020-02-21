package commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;


public class KickCommand extends Command {

    public KickCommand() {
        this.name = "kick";
        this.arguments = "<user>";
        this.help = "kick user from server";
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.botPermissions = new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        TextChannel channel = commandEvent.getTextChannel();
        StringBuilder message = new StringBuilder();
        boolean membersKicked = false;

        for(Member member : commandEvent.getMessage().getMentionedMembers()) {
            if(commandEvent.getMember().canInteract(member) && commandEvent.getSelfMember().canInteract(member)) {
                member.kick().queue(
                        (m) -> {
                            message.append(member.getEffectiveName()).append(" kicked\n");
                        }, (t) -> {
                            message.append(member.getEffectiveName()).append(" not kicked\n")
                                    .append(t.getMessage());
                        }
                );
                membersKicked = true;
            } else {
                message.append("Cannot kick ").append(member.getEffectiveName()).append(" with equal or higher role\n");
            }
        }

        if(!membersKicked && message.length() == 0) {
            message.append("No mentioned users to kick");
        }

        channel.sendMessage(message)
                .queue();
    }
}
