package commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

    public final AudioPlayer player;

    public final TrackScheduler scheduler;

    public final Long guildId;

    GuildMusicManager(AudioPlayerManager audioPlayerManager, long guildId) {
        this.player = audioPlayerManager.createPlayer();
        this.guildId = guildId;
        this.scheduler = new TrackScheduler(player, guildId);
        player.addListener(scheduler);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }
}
