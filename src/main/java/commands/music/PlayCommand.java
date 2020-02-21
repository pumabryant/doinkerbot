package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand extends Command {

    public PlayCommand() {
        this.name = "play";
        this.help = "play song url";
        this.arguments = "<url>";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String url = commandEvent.getArgs();
        Guild guild = commandEvent.getGuild();
        Member member = commandEvent.getMember();
        TextChannel channel = commandEvent.getTextChannel();

        if(member.getVoiceState().inVoiceChannel() && isUserInMusicChannel(guild, member)) {
            loadAndPlay(guild, channel, url, member);
        } else {
            channel.sendMessage("Must be in voice channel to use command")
                    .queue();
        }
    }

    private void loadAndPlay(Guild guild, TextChannel channel, String url, Member member) {
        GuildMusicManager guildMusicManager = ManagerFactory.getGuildMusicManagers(guild);

        ManagerFactory.audioManager.loadItemOrdered(guildMusicManager, url, new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack audioTrack) {

                        channel.sendMessage("\"" + audioTrack.getInfo().title + "\" added to queue")
                                .queue();

                        play(guild, guildMusicManager, audioTrack, member);
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist audioPlaylist) {
                        AudioTrack audioTrack = audioPlaylist.getSelectedTrack();

                        if(audioTrack == null) {
                            audioTrack = audioPlaylist.getTracks().get(0);
                        }

                        channel.sendMessage("\"" + audioTrack.getInfo().title + "\" added to queue")
                                .queue();


                        play(guild, guildMusicManager, audioTrack, member);
                    }

                    @Override
                    public void noMatches() {
                        channel.sendMessage("No matches found for: " + url)
                                .queue();
                    }

                    @Override
                    public void loadFailed(FriendlyException e) {
                        channel.sendMessage("Could not play track")
                                .queue();
                    }
                }
        );
    }

    private void play(Guild guild, GuildMusicManager guildMusicManager, AudioTrack audioTrack, Member member) {
        connectToVoiceChannel(guild, guild.getAudioManager());
        guildMusicManager.scheduler.queue(audioTrack);
    }

    private boolean isUserInMusicChannel(Guild guild, Member member) {
        if (guild.getAudioManager().getConnectionStatus() == ConnectionStatus.NOT_CONNECTED) {
            return true;
        } else if (guild.getAudioManager().getConnectionStatus() == ConnectionStatus.CONNECTED) {
            if(member.getVoiceState().getChannel().equals(
                    guild.getAudioManager().getConnectedChannel())) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param guild
     * @param audioManager
     * @return
     */
    private void connectToVoiceChannel(Guild guild, AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for(VoiceChannel channel : guild.getVoiceChannels()) {
                audioManager.openAudioConnection(guild.getVoiceChannels().get(0));
                break;
            }
        }
    }
}
