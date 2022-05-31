package com.mawuote.api.manager.misc;

import java.nio.file.attribute.*;
import com.mawuote.*;
import com.mawuote.api.manager.module.*;
import java.nio.file.*;
import com.mawuote.api.manager.value.*;
import java.awt.*;
import com.mawuote.api.manager.value.impl.*;
import java.util.*;
import java.nio.charset.*;
import java.io.*;
import com.mawuote.api.manager.element.*;
import com.google.gson.*;
import com.mawuote.api.manager.friend.*;

public class ConfigManager
{
    public void load() {
        try {
            this.loadModules();
            this.loadElements();
            this.loadPrefix();
            this.loadFriends();
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public void save() {
        try {
            if (!Files.exists(Paths.get("Kaotik/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Kaotik/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (!Files.exists(Paths.get("Kaotik/Modules/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Kaotik/Modules/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (!Files.exists(Paths.get("Kaotik/Elements/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Kaotik/Elements/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (!Files.exists(Paths.get("Kaotik/Client/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Kaotik/Client/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            for (final ModuleCategory category : ModuleCategory.values()) {
                if (!category.equals(ModuleCategory.HUD)) {
                    if (!Files.exists(Paths.get("Kaotik/Modules/" + category.getName() + "/", new String[0]), new LinkOption[0])) {
                        Files.createDirectories(Paths.get("Kaotik/Modules/" + category.getName() + "/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
                    }
                }
            }
            this.saveModules();
            this.saveElements();
            this.savePrefix();
            this.saveFriends();
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public void attach() {
        Runtime.getRuntime().addShutdownHook(new SaveThread());
    }
    
    public void loadModules() throws IOException {
        for (final Module module : Kaotik.MODULE_MANAGER.getModules()) {
            if (module.getCategory().equals(ModuleCategory.HUD)) {
                continue;
            }
            if (!Files.exists(Paths.get("Kaotik/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
                continue;
            }
            final InputStream stream = Files.newInputStream(Paths.get("Kaotik/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new OpenOption[0]);
            JsonObject moduleJson;
            try {
                moduleJson = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
            }
            catch (final IllegalStateException exception) {
                exception.printStackTrace();
                Kaotik.LOGGER.error(module.getName());
                Kaotik.LOGGER.error("Bailing out. You are on your own. Good luck.");
                continue;
            }
            if (moduleJson.get("Name") == null) {
                continue;
            }
            if (moduleJson.get("Status") == null) {
                continue;
            }
            if (moduleJson.get("Status").getAsBoolean()) {
                module.enable();
            }
            final JsonObject valueJson = moduleJson.get("Values").getAsJsonObject();
            for (final Value value : module.getValues()) {
                final JsonElement dataObject = valueJson.get(value.getName());
                if (dataObject != null && dataObject.isJsonPrimitive()) {
                    if (value instanceof ValueBoolean) {
                        ((ValueBoolean)value).setValue(dataObject.getAsBoolean());
                    }
                    else if (value instanceof ValueNumber) {
                        if (((ValueNumber)value).getType() == 1) {
                            ((ValueNumber)value).setValue(dataObject.getAsInt());
                        }
                        else if (((ValueNumber)value).getType() == 2) {
                            ((ValueNumber)value).setValue(dataObject.getAsDouble());
                        }
                        else {
                            if (((ValueNumber)value).getType() != 3) {
                                continue;
                            }
                            ((ValueNumber)value).setValue(dataObject.getAsFloat());
                        }
                    }
                    else if (value instanceof ValueEnum) {
                        ((ValueEnum)value).setValue(((ValueEnum)value).getEnumByName(dataObject.getAsString()));
                    }
                    else if (value instanceof ValueString) {
                        ((ValueString)value).setValue(dataObject.getAsString());
                    }
                    else if (value instanceof ValueColor) {
                        ((ValueColor)value).setValue(new Color(dataObject.getAsInt()));
                        if (valueJson.get(value.getName() + "-Rainbow") != null) {
                            ((ValueColor)value).setRainbow(valueJson.get(value.getName() + "-Rainbow").getAsBoolean());
                        }
                        if (valueJson.get(value.getName() + "-Alpha") == null) {
                            continue;
                        }
                        ((ValueColor)value).setValue(new Color(((ValueColor)value).getValue().getRed(), ((ValueColor)value).getValue().getGreen(), ((ValueColor)value).getValue().getBlue(), valueJson.get(value.getName() + "-Alpha").getAsInt()));
                    }
                    else {
                        if (!(value instanceof ValueBind)) {
                            continue;
                        }
                        ((ValueBind)value).setValue(dataObject.getAsInt());
                    }
                }
            }
            stream.close();
        }
    }
    
    public void saveModules() throws IOException {
        for (final Module module : Kaotik.MODULE_MANAGER.getModules()) {
            if (module.getCategory().equals(ModuleCategory.HUD)) {
                continue;
            }
            if (Files.exists(Paths.get("Kaotik/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
                final File file = new File("Kaotik/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json");
                file.delete();
            }
            Files.createFile(Paths.get("Kaotik/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final JsonObject moduleJson = new JsonObject();
            final JsonObject valueJson = new JsonObject();
            moduleJson.add("Name", new JsonPrimitive(module.getName()));
            moduleJson.add("Status", new JsonPrimitive(module.isToggled()));
            for (final Value value : module.getValues()) {
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBoolean)value).getValue()));
                }
                else if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueNumber)value).getValue()));
                }
                else if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueEnum)value).getValue().name()));
                }
                else if (value instanceof ValueString) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueString)value).getValue()));
                }
                else if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", new JsonPrimitive(((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", new JsonPrimitive(((ValueColor)value).getRainbow()));
                }
                else {
                    if (!(value instanceof ValueBind)) {
                        continue;
                    }
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBind)value).getValue()));
                }
            }
            moduleJson.add("Values", valueJson);
            final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Kaotik/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"), StandardCharsets.UTF_8);
            writer.write(gson.toJson(new JsonParser().parse(moduleJson.toString())));
            writer.close();
        }
    }
    
    public void loadElements() throws IOException {
        for (final Element element : Kaotik.ELEMENT_MANAGER.getElements()) {
            if (!Files.exists(Paths.get("Kaotik/Elements/" + element.getName() + ".json", new String[0]), new LinkOption[0])) {
                continue;
            }
            final InputStream stream = Files.newInputStream(Paths.get("Kaotik/Elements/" + element.getName() + ".json", new String[0]), new OpenOption[0]);
            final JsonObject elementJson = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
            if (elementJson.get("Name") == null || elementJson.get("Status") == null) {
                continue;
            }
            if (elementJson.get("Positions") == null) {
                continue;
            }
            if (elementJson.get("Status").getAsBoolean()) {
                element.enable();
            }
            final JsonObject valueJson = elementJson.get("Values").getAsJsonObject();
            final JsonObject positionJson = elementJson.get("Positions").getAsJsonObject();
            for (final Value value : element.getValues()) {
                final JsonElement dataObject = valueJson.get(value.getName());
                if (dataObject != null && dataObject.isJsonPrimitive()) {
                    if (value instanceof ValueBoolean) {
                        ((ValueBoolean)value).setValue(dataObject.getAsBoolean());
                    }
                    else if (value instanceof ValueNumber) {
                        if (((ValueNumber)value).getType() == 1) {
                            ((ValueNumber)value).setValue(dataObject.getAsInt());
                        }
                        else if (((ValueNumber)value).getType() == 2) {
                            ((ValueNumber)value).setValue(dataObject.getAsDouble());
                        }
                        else {
                            if (((ValueNumber)value).getType() != 3) {
                                continue;
                            }
                            ((ValueNumber)value).setValue(dataObject.getAsFloat());
                        }
                    }
                    else if (value instanceof ValueEnum) {
                        ((ValueEnum)value).setValue(((ValueEnum)value).getEnumByName(dataObject.getAsString()));
                    }
                    else if (value instanceof ValueString) {
                        ((ValueString)value).setValue(dataObject.getAsString());
                    }
                    else if (value instanceof ValueColor) {
                        ((ValueColor)value).setValue(new Color(dataObject.getAsInt()));
                        if (valueJson.get(value.getName() + "-Rainbow") != null) {
                            ((ValueColor)value).setRainbow(valueJson.get(value.getName() + "-Rainbow").getAsBoolean());
                        }
                        if (valueJson.get(value.getName() + "-Alpha") == null) {
                            continue;
                        }
                        ((ValueColor)value).setValue(new Color(((ValueColor)value).getValue().getRed(), ((ValueColor)value).getValue().getGreen(), ((ValueColor)value).getValue().getBlue(), valueJson.get(value.getName() + "-Alpha").getAsInt()));
                    }
                    else {
                        if (!(value instanceof ValueBind)) {
                            continue;
                        }
                        ((ValueBind)value).setValue(dataObject.getAsInt());
                    }
                }
            }
            if (positionJson.get("X") != null && positionJson.get("Y") != null) {
                element.frame.setX(positionJson.get("X").getAsFloat());
                element.frame.setY(positionJson.get("Y").getAsFloat());
            }
            stream.close();
        }
    }
    
    public void saveElements() throws IOException {
        for (final Element element : Kaotik.ELEMENT_MANAGER.getElements()) {
            if (Files.exists(Paths.get("Kaotik/Elements/" + element.getName() + ".json", new String[0]), new LinkOption[0])) {
                final File file = new File("Kaotik/Elements/" + element.getName() + ".json");
                file.delete();
            }
            Files.createFile(Paths.get("Kaotik/Elements/" + element.getName() + ".json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final JsonObject elementJson = new JsonObject();
            final JsonObject valueJson = new JsonObject();
            final JsonObject positionJson = new JsonObject();
            elementJson.add("Name", new JsonPrimitive(element.getName()));
            elementJson.add("Status", new JsonPrimitive(element.isToggled()));
            for (final Value value : element.getValues()) {
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBoolean)value).getValue()));
                }
                else if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueNumber)value).getValue()));
                }
                else if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueEnum)value).getValue().name()));
                }
                else if (value instanceof ValueString) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueString)value).getValue()));
                }
                else if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", new JsonPrimitive(((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", new JsonPrimitive(((ValueColor)value).getRainbow()));
                }
                else {
                    if (!(value instanceof ValueBind)) {
                        continue;
                    }
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBind)value).getValue()));
                }
            }
            positionJson.add("X", new JsonPrimitive(element.frame.getX()));
            positionJson.add("Y", new JsonPrimitive(element.frame.getY()));
            elementJson.add("Values", valueJson);
            elementJson.add("Positions", positionJson);
            final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Kaotik/Elements/" + element.getName() + ".json"), StandardCharsets.UTF_8);
            writer.write(gson.toJson(new JsonParser().parse(elementJson.toString())));
            writer.close();
        }
    }
    
    public void loadPrefix() throws IOException {
        if (!Files.exists(Paths.get("Kaotik/Client/Prefix.json", new String[0]), new LinkOption[0])) {
            return;
        }
        final InputStream stream = Files.newInputStream(Paths.get("Kaotik/Client/Prefix.json", new String[0]), new OpenOption[0]);
        final JsonObject prefixJson = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
        if (prefixJson.get("Prefix") == null) {
            return;
        }
        Kaotik.COMMAND_MANAGER.setPrefix(prefixJson.get("Prefix").getAsString());
        stream.close();
    }
    
    public void savePrefix() throws IOException {
        if (Files.exists(Paths.get("Kaotik/Client/Prefix.json", new String[0]), new LinkOption[0])) {
            final File file = new File("Kaotik/Client/Prefix.json");
            file.delete();
        }
        Files.createFile(Paths.get("Kaotik/Client/Prefix.json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonObject prefixJson = new JsonObject();
        prefixJson.add("Prefix", new JsonPrimitive(Kaotik.COMMAND_MANAGER.getPrefix()));
        final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Kaotik/Client/Prefix.json"), StandardCharsets.UTF_8);
        writer.write(gson.toJson(new JsonParser().parse(prefixJson.toString())));
        writer.close();
    }
    
    public void loadFriends() throws IOException {
        if (!Files.exists(Paths.get("Kaotik/Client/Friends.json", new String[0]), new LinkOption[0])) {
            return;
        }
        final InputStream stream = Files.newInputStream(Paths.get("Kaotik/Client/Friends.json", new String[0]), new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject();
        if (mainObject.get("Friends") == null) {
            return;
        }
        final JsonArray friendArray = mainObject.get("Friends").getAsJsonArray();
        friendArray.forEach(friend -> Kaotik.FRIEND_MANAGER.addFriend(friend.getAsString()));
        stream.close();
    }
    
    public void saveFriends() throws IOException {
        if (Files.exists(Paths.get("Kaotik/Client/Friends.json", new String[0]), new LinkOption[0])) {
            final File file = new File("Kaotik/Client/Friends.json");
            file.delete();
        }
        Files.createFile(Paths.get("Kaotik/Client/Friends.json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonObject mainObject = new JsonObject();
        final JsonArray friendArray = new JsonArray();
        for (final Friend friend : Kaotik.FRIEND_MANAGER.getFriends()) {
            friendArray.add(friend.getName());
        }
        mainObject.add("Friends", friendArray);
        final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Kaotik/Client/Friends.json"), StandardCharsets.UTF_8);
        writer.write(gson.toJson(new JsonParser().parse(mainObject.toString())));
        writer.close();
    }
    
    public static class SaveThread extends Thread
    {
        @Override
        public void run() {
            Kaotik.CONFIG_MANAGER.save();
        }
    }
}
