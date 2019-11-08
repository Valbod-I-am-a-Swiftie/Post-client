package ikpi63holding.postclient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonSerialize(using = MailFolderSetSerializer.class)
@JsonDeserialize(using = MailFolderSetDeserializer.class)
public class MailFolderSet {
    private static final String LIST_DELIM = ", ";
    private static final String TYPE_DELIM = ":";

    private Set<String> nameSet = new TreeSet<>();
    private Map<FolderType, String> typeMap = new EnumMap<>(FolderType.class);

    MailFolderSet(String parse) {
        Arrays.stream(parse.split(LIST_DELIM)).forEach(s -> {
            var parts = s.split(TYPE_DELIM);
            if (parts.length > 1) {
                FolderType folderType = FolderType.valueOf(parts[1]);
                addFromString(parts[0], parts[1]);
            } else {
                addFolder(parts[0]);
            }
        });
    }


    MailFolderSet(List<MailFolderJsonObject> jsonMap) {
        if (jsonMap == null)
            return;
        jsonMap.forEach(mailFolderJsonObject -> this.addFromString(mailFolderJsonObject.name, mailFolderJsonObject.type));
    }

    public boolean contains(String name) {
        return nameSet.contains(name);
    }

    public String getFolder(FolderType type) {
        return typeMap.getOrDefault(type, null);
    }

    public void addFolder(String name) {
        nameSet.add(name);
    }

    public void addFolder(String name, FolderType type) {
        addFolder(name);
        if (type != FolderType.NONE) {
            typeMap.put(type, name);
        }
    }

    private void addFromString(String name, String typeString) {
        addFolder(name);
        FolderType type = FolderType.valueOf(typeString);
        if (type != FolderType.NONE) {
            typeMap.putIfAbsent(type, name);
        }
    }

    private FolderType findType(String name) {
        return typeMap.entrySet().stream()
                .filter(folderTypeStringEntry -> folderTypeStringEntry.getValue().equals(name)).map(
                        Map.Entry::getKey).findFirst().orElse(FolderType.NONE);
    }

    public void updateSet(List<String> nameList) {
        boolean removed = nameSet.retainAll(nameList);
        if (removed) {
            typeMap.entrySet().removeIf(
                    folderTypeStringEntry -> !nameSet.contains(folderTypeStringEntry.getValue()));
        }
        nameSet.addAll(nameList);
    }

    private String withType(String name) {
        FolderType type = findType(name);
        return name + (type != FolderType.NONE ? TYPE_DELIM + type.toString() : "");
    }

    public List<MailFolderJsonObject> getMap() {
        return nameSet.stream().map(s -> new MailFolderJsonObject(s, findType(s).toString()))
                .collect(Collectors.toCollection(
                        ArrayList::new));
    }

//    @JsonSetter("folders")
//    public void setMap(List<MailFolderJsonObject> jsonMap) {
//        jsonMap.forEach(mailFolderJsonObject -> this.addFromString(mailFolderJsonObject.name, mailFolderJsonObject.type));
//    }

    @Override
    public String toString() {
        return nameSet.stream().map(this::withType).collect(Collectors.joining(LIST_DELIM));
    }

}

