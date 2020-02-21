package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends Command {

    private static final int MAX_ELEMENTS = 5;

    public QueueCommand() {
        this.name = "queue";
        this.help = "show up to the 5 next queued tracks";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        Guild guild = commandEvent.getGuild();
        TextChannel channel = commandEvent.getTextChannel();

        showQueue(guild, channel);
    }

    private void showQueue(Guild guild, TextChannel channel) {
        StringBuilder sb = new StringBuilder();

        BlockingQueue queue = ManagerFactory.getGuildMusicManagers(guild).scheduler.getQueue();

        String message = queue.isEmpty() ? "No tracks in queue" :
                "Queue: \n\t" + queueToString(sb, queue);

        channel.sendMessage(message)
                .queue();
    }

    private String queueToString(StringBuilder sb, BlockingQueue queue) {
        int count = 0;

        for(Iterator iter = queue.iterator(); iter.hasNext(); count++) {
            AudioTrack track = (AudioTrack) iter.next();
            if(count < MAX_ELEMENTS) {
                sb.append(count).append(": ").append(track.getInfo().title).append("\n");
            } else {
                break;
            }
        }

        return sb.toString();
    }

}
