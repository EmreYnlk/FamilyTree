package com.example.familytree;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Json_WriterandReader {

    public static void writeFamilyTree(String familyTreeName,human root) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HumanDTO rootDTO = HumanMapper.convertToDTO(root);
        List<HumanDTO> allMembers = new ArrayList<>();
        Queue<human> queue = new LinkedList<>();
        Set<Double> visited = new HashSet<>();

        queue.add(root);
        visited.add(root.personalid);

        while (!queue.isEmpty()) {
            human current = queue.poll();
            allMembers.add(HumanMapper.convertToDTO(current));

            for (int i = 0; i < current.childlist.getSize(); i++) {
                human child = current.childlist.get(i);
                if (!visited.contains(child.personalid)) {
                    queue.add(child);
                    visited.add(child.personalid);
                }
            }

            if (current.partner != null && !visited.contains(current.partner.personalid)) {
                queue.add(current.partner);
                visited.add(current.partner.personalid);
            }
        }

        Map<String, Object> jsonData = new LinkedHashMap<>();
        jsonData.put("familytreename", familyTreeName);
        jsonData.put("root", rootDTO);
        jsonData.put("allMembers", allMembers);


        File folder = new File("src/main/java/Jsonlar/");

        File file123 = new File(folder, familyTreeName + ".json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(file123, jsonData);
    }









    public static class HumanDTO {
        public String personalid;
        public String name;
        public String surname;
        public String cinsiyet;
        public String bornyear;
        public PartnerDTO partner;
        public String parentId;
        public List<String> childrenIds;
    }

    public static class PartnerDTO {
        public String name;
        public String surname;
        public String bornyear;
        public String personalid;
    }

    public static class HumanMapper {
        public static HumanDTO convertToDTO(human h) {
            HumanDTO dto = new HumanDTO();
            dto.personalid = String.valueOf((int) h.personalid);
            dto.name = h.name;
            dto.surname = h.surname;
            dto.cinsiyet = String.valueOf(h.cinsiyet);
            dto.bornyear = h.bornyear;

            if (h.partner != null) {
                PartnerDTO p = new PartnerDTO();
                p.name = h.partner.name;
                p.surname = h.partner.surname;
                p.bornyear = h.partner.bornyear;
                p.personalid = String.valueOf((int) h.partner.personalid);
                dto.partner = p;
            }

            dto.parentId = h.parent != null ? String.valueOf((int) h.parent.personalid) : null;

            dto.childrenIds = new ArrayList<>();
            for (int i = 0; i < h.childlist.getSize(); i++) {
                human child = h.childlist.get(i);
                dto.childrenIds.add(String.valueOf((int) child.personalid));
            }

            return dto;
        }
    }
}
