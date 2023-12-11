package ink.anh.lingo.file;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ink.anh.api.lingo.Translator;
import ink.anh.api.messages.MessageType;
import ink.anh.api.messages.Messenger;
import ink.anh.api.utils.LangUtils;
import ink.anh.lingo.AnhyLingo;
import ink.anh.lingo.GlobalManager;

public class DirectoryContents {

	private static GlobalManager globalManager;
	
	static {
		globalManager = AnhyLingo.getInstance().getGlobalManager();
	}
    public static void listDirectoryContents(CommandSender sender, String directoryPath) {
    	if (directoryPath.equals("0")) directoryPath = "";
    	String[] langs = (sender instanceof Player) ? LangUtils.getPlayerLanguage((Player) sender, globalManager.getPlugin()) : null;
    	String pluginName = AnhyLingo.getInstance().getGlobalManager().getPluginName() + ": ";

        File directory = new File(AnhyLingo.getInstance().getServer().getWorldContainer(), "plugins" + File.separator + directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] fileList = directory.listFiles();

            if (fileList != null) {
            	String iconFolder = "📁 ";
            	String iconFile = "📄 ";
                // Сортування файлів та папок
                Arrays.sort(fileList, Comparator.comparing(File::isFile)
                                                .thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER));

                
                Messenger.sendMessage(globalManager, sender, "lingo_file_folder_contents " + iconFolder + directoryPath, MessageType.IMPORTANT);
                for (File file : fileList) {
                    if (file.isDirectory()) {
                    	Messenger.sendShowFolder(globalManager, sender, directoryPath, file.getName(), iconFolder, MessageType.IMPORTANT, langs);
                    } else {
                    	Messenger.sendMessageSimple(globalManager, sender, file.getName(), iconFile, MessageType.ESPECIALLY);
                    }
                }
            } else {
            	sender.sendMessage(pluginName + Translator.translateKyeWorld(globalManager, "lingo_err_folder_is_empty", langs));
            }
        } else {
        	sender.sendMessage(pluginName + Translator.translateKyeWorld(globalManager, "lingo_err_folder_is_notexist ", langs));
        }
    }
}
