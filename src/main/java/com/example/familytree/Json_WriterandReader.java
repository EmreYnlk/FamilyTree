package com.example.familytree;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Json_WriterandReader {

    public static void writeFamilyTree(String familyTreeName,human root) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        List<HumanDTO> allMembers = new ArrayList<>();
        allMembers.add(convertToDTO(root));  // Sadece root ve iç içe çocukları

        Map<String, Object> jsonData = new LinkedHashMap<>();
        jsonData.put("familytreename", familyTreeName);
        jsonData.put("allMembers", allMembers); // Root ve çocukları JSON içinde gömülü

        File folder = new File("src/main/java/Jsonlar/");
        File file123 = new File(folder, familyTreeName + ".json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(file123, jsonData);
    }



    public static human readFamilyTree(String familyTreeName) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        File folder = new File("src/main/java/Jsonlar/");
        File file123 = new File(folder, familyTreeName + ".json");


        Map<?, ?> jsonData = mapper.readValue(file123, Map.class);
        List<?> allMembersList = (List<?>) jsonData.get("allMembers");
        Object rootRaw = allMembersList.get(0);       //ilk member root
        HumanDTO rootDTO = mapper.convertValue(rootRaw, HumanDTO.class);
        return convertToHuman(rootDTO, null);
    }








    public static class HumanDTO {
        public String personalid;
        public String name;
        public String surname;
        public String cinsiyet;
        public String bornyear;
        public PartnerDTO partner;
        public List<HumanDTO> children;

    }

    public static class PartnerDTO {
        public String name;
        public String surname;
        public String bornyear;
        public String personalid;
    }

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

        dto.children = new ArrayList<>();
        for (int i = 0; i < h.childlist.getSize(); i++) {
            human child = h.childlist.get(i);
            dto.children.add(convertToDTO(child)); // Recursive
        }

        return dto;
    }

    public static human convertToHuman(HumanDTO dto, human parent) {
        human h = new human("","","",' ');
        h.personalid = Double.parseDouble(dto.personalid);
        h.name = dto.name;
        h.surname = dto.surname;
        h.cinsiyet = dto.cinsiyet.charAt(0);
        h.bornyear = dto.bornyear;
        h.parent = parent;

        if (dto.partner != null) {
            char cinsiyeti = (h.cinsiyet == 'E') ? 'K' : 'E';
            human p = new human("","","",cinsiyeti);
            p.name = dto.partner.name;
            p.surname = dto.partner.surname;
            p.bornyear = dto.partner.bornyear;
            p.personalid = Double.parseDouble(dto.partner.personalid);
            h.partner = p;
        }

        if (dto.children != null) {
            for (HumanDTO childDTO : dto.children) {
                human child = convertToHuman(childDTO, h); // recursive
                h.childlist.add(child);
            }
        }

        return h;
    }
}