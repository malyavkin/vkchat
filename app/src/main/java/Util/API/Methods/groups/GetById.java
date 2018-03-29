package Util.API.Methods.groups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import Persistence.Entities.Group.Group;
import Util.API.Method;

public class GetById extends Method<Group> {

    public GetById(String id) {
        params = new HashMap<>();
        params.put("group_ids", id);
    }

    public GetById(List<String> ids) {
        params = new HashMap<>();
        //TODO: this

        StringBuilder idsString = new StringBuilder();
        for (String id : ids) {
            if (idsString.length() > 0) {
                idsString.append(",");
            }
            idsString.append(id);
        }

        params.put("group_ids", idsString.toString());
    }

    @Override
    public String getMethod() {
        return "groups.getById";
    }

    @Override
    public HashMap<String, Group> parseResult(JSONObject response) throws JSONException {
        HashMap<String, Group> groups = new HashMap<>();

        JSONArray oResponse = response.getJSONArray("response");
        for (int i = 0; i < oResponse.length(); i++) {
            JSONObject oEntity = oResponse.getJSONObject(i);

            Group g = new Group(
                    oEntity.getInt("id"),
                    oEntity.getString("name")
            );
            groups.put(oEntity.getString("id"), g);

        }

        return groups;
    }


}
