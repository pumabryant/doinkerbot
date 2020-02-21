package bot;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import commands.admin.BanCommand;
import commands.admin.KickCommand;
import commands.general.UserInfoCommand;
import commands.music.ManagerFactory;
import commands.music.PlayCommand;
import commands.music.QueueCommand;
import commands.music.SkipCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {

    public static JDA bot;

    public static void main(String[] args) throws LoginException {

        AudioSourceManagers.registerLocalSource(ManagerFactory.audioManager);
        AudioSourceManagers.registerRemoteSources(ManagerFactory.audioManager);

        CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
                .setPrefix("!")
                .setOwnerId("132716300987269120")
                .addCommands(
                        new UserInfoCommand(),
                        new PlayCommand(),
                        new SkipCommand(),
                        new QueueCommand(),
                        new KickCommand(),
                        new BanCommand()
                );

        bot = new JDABuilder(args[0]).
                addEventListeners(commandClientBuilder.build())
                .build();
    }
}
