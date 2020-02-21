package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand extends Command {

    public SkipCommand() {
        this.name = "skip";
        this.help = "skip currently playing track";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        Guild guild = commandEvent.getGuild();
        TextChannel channel = commandEvent.getTextChannel();

        skip(guild, channel);
    }

    private void skip(Guild guild, TextChannel channel) {

        GuildMusicManager guildMusicManager = ManagerFactory.getGuildMusicManagers(guild);
        AudioTrack track = guildMusicManager.player.getPlayingTrack();
        guildMusicManager.scheduler.playNext();

        String message = track == null ? "No track playing to skip" :
                "Skipping current track: " + track.getInfo().title;

        channel.sendMessage(message)
                .queue();
    }
}
