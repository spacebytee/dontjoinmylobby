package com.bytespacegames.dontjoinmylobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegexManager {
    public static RegexManager INSTANCE;
    private final List<String> patterns = new ArrayList<String>();
    public RegexManager() {
        INSTANCE = this;
        load();
    }
    public void load() {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/dontjoinmylobby/regexes.txt")) {
            if (stream == null) {
                DontJoinMyLobby.LOGGER.error("regexes.txt not found in resources/assets/dontjoinmylobby/");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                patterns.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> getPatterns() {
        return patterns;
    }
}