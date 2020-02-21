package commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserInfoCommand extends Command {

    public UserInfoCommand() {
        this.name = "info";
    }

    protected void execute(CommandEvent commandEvent) {

        Guild guild = commandEvent.getGuild();
        Member member = commandEvent.getMessage().getMentionedMembers().isEmpty() ?
                commandEvent.getMessage().getMember() : commandEvent.getMessage().getMentionedMembers().get(0);
        LocalDate joinDate = member.getTimeJoined().toLocalDate();

        commandEvent.reply(
                member.getAsMention() + " joined: " +
                        guild.getName() + " on " +
                        joinDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        );
    }
}
