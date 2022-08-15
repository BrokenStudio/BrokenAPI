package dev.brokenstudio.brokenapi.language;

import dev.brokenstudio.brokenapi.BrokenAPI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class LanguageAPI {

    private HashMap<Integer, LanguageRepository> languages;
    private HashMap<UUID, Integer> selectedLanguage;

    // lang_id lang_set lang_key lang_text

    {
        languages = new HashMap<>();
        selectedLanguage = new HashMap<>();
    }

    public void loadLanguageSet(String type){
        Connection con = null;
        ResultSet rs = null;
        try {
            con = BrokenAPI.api().getDatabaseHandler().getMariaDBHandler().getSQLConnection().getDriverConnection();
            rs = con.prepareStatement(
                    "SELECT * FROM `api_lang` WHERE `lang_set`='" + type + "';"
            ).executeQuery();
            while(rs.next()){
                languages.get(rs.getInt("lang_id")).add(rs.getString("lang_key"),
                        rs.getString("lang_text"));
            }
        }catch (SQLException ignored){}
        finally {
            try {
                rs.close();
                con.close();
            }catch (SQLException ig){}
        }
    }

    public void setSelectedLanguage(UUID uuid, int lang){
        selectedLanguage.put(uuid, lang);
    }

    public LanguageRepository getSelectedLanguage(UUID uuid){
        return getRepository(selectedLanguage.get(uuid));
    }

    public LanguageRepository getRepository(Integer id){
        return languages.get(id);
    }

}
