package commands.music;

import bot.DiscordBot;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private BlockingQueue<AudioTrack> queue;
    private Long guildId;

    TrackScheduler(AudioPlayer player, Long guildId) {
        this.player = player;
        this.guildId = guildId;
        queue = new LinkedBlockingQueue();
    }

    public void queue(AudioTrack track) {
        if(!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public boolean playNext() {
        return player.startTrack(queue.poll(), false);
    }

    public BlockingQueue getQueue(){
        return this.queue;
    }


    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {

    }


    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext) {
            if(!playNext()) {
//                boolean isVoiceChannelEmpty = Optional.ofNullable(DiscordBot.bot.getGuildById(guildId))
//                        .map(g -> g.getAudioManager())
//                        .map(a -> a.getConnectedChannel())
//                        .map(c -> c.getMembers())
//                        .map(m -> m.size() == 1)
//                        .orElse(false);
//                if(isVoiceChannelEmpty) {
//                    DiscordBot.bot.getGuildById(guildId)
//                            .getAudioManager()
//                            .closeAudioConnection();
//                }
                if(DiscordBot.bot.getGuildById(guildId).getAudioManager().getConnectedChannel().getMembers().size() == 1) {
                    DiscordBot.bot.getGuildById(guildId).getAudioManager().closeAudioConnection();
                }
            }
        }
    }
}
