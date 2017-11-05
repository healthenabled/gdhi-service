package it.gdhi.model;

import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public final class HealthScoreToColor {

    private static Map<Integer, String> healthScoreToColorMap = new HashMap<>();

    private HealthScoreToColor() {
    }

    static {
        ymlToMap();
    }

    public static String scoreToColor(Integer score) {
        return healthScoreToColorMap.get(score);
    }

    private static void ymlToMap() {
        Yaml yaml = new Yaml();
        try {
            File file = ResourceUtils.getFile("healthscore-color-map.yml");
            FileInputStream fileInputStream = new FileInputStream(file);
            healthScoreToColorMap = (Map<Integer, String>) yaml.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
