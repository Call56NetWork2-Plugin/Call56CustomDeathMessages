package com.kinoko2k.call56customdeathmessages.other;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kinoko2k.call56customdeathmessages.Call56CustomDeathMessages;
import com.kinoko2k.call56customdeathmessages.enums.VersionEnums;
import net.md_5.bungee.api.ChatColor;

public class HexChat {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final char COLOR_CHAR = '\u00A7';

    public static String translateHexCodes(String message, Call56CustomDeathMessages plugin)
    {
        return translate(HEX_PATTERN, message, plugin);
    }

    public static String translateHexCodes(String startTag, String endTag, String message, Call56CustomDeathMessages plugin)
    {
        final Pattern hexPattern = Pattern.compile(startTag + "([a-f0-9]{6})" + endTag);
        return translate(hexPattern, message, plugin);
    }

    private static String translate(Pattern hex, String message, Call56CustomDeathMessages plugin)
    {
        if (plugin.getServerVersion().getVersionInt() >= VersionEnums.VERSION_116.getVersionInt())
        {
            Matcher matcher = hex.matcher(message);
            StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
            while (matcher.find())
            {
                String group = matcher.group(1);
                matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                        + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                        + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                        + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
                );
            }
            return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
        }
        else
        {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }
}