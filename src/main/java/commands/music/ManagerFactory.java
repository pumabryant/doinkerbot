package commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class ManagerFactory {

    private static final Map<Long, GuildMusicManager> guildMusicManagers = new HashMap<>();
    public static final AudioPlayerManager audioManager = new DefaultAudioPlayerManager();

    synchronized static GuildMusicManager getGuildMusicManagers(Guild guild) {
        Long guildId = Long.parseLong(guild.getId());

        GuildMusicManager guildMusicManager = guildMusicManagers.get(guildId);

        if(guildMusicManager == null) {
            guildMusicManager = new GuildMusicManager(audioManager, guildId);
            guildMusicManagers.put(guildId, guildMusicManager);
        }

        guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

        return guildMusicManager;
    }
}
