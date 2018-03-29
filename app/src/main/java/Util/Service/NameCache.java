package Util.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Persistence.Entities.Group.Group;
import Persistence.Entities.User.User;
import Util.API.APIRequestBuilder;
import Util.Downloader.Downloaders.GroupDownloader;
import Util.Downloader.Downloaders.UserDownloader;
import Util.Listener;

public class NameCache {
    private HashMap<String, User> users;
    private HashMap<String, Group> groups;
    private APIRequestBuilder api;

    public NameCache(APIRequestBuilder api) {
        setup();
        this.api = api;
    }

    public NameCache(String token) {
        setup();
        this.api = new APIRequestBuilder(token);
    }

    private void setup() {
        users = new HashMap<>();
        groups = new HashMap<>();
    }

    public void getGroup(String groupId, final Listener<HashMap<String, Group>> callback) {
        getGroups(Collections.singletonList(groupId), callback);
    }

    public void getGroups(List<String> groupIds, final Listener<HashMap<String, Group>> callback) {
        ArrayList<String> requestIds = new ArrayList<>();
        for (String id : groupIds) {
            if (!groups.containsKey(id)) {
                requestIds.add(id);
            }
        }

        Listener<HashMap<String, Group>> listener = new Listener<HashMap<String, Group>>() {
            @Override
            public void call(HashMap<String, Group> param) {
                groups.putAll(param);
                callback.call(groups);
            }
        };
        if (requestIds.size() != 0) {
            new GroupDownloader(api, listener, requestIds);
        } else {
            callback.call(groups);
        }

    }


    public void getUser(String personId, final Listener<HashMap<String, User>> callback) {
        getUsers(Collections.singletonList(personId), callback);
    }

    public void getUsers(List<String> personIds, final Listener<HashMap<String, User>> callback) {
        ArrayList<String> requestIds = new ArrayList<>();
        for (String id : personIds) {
            if (!users.containsKey(id)) {
                requestIds.add(id);
            }
        }

        Listener<HashMap<String, User>> listener = new Listener<HashMap<String, User>>() {
            @Override
            public void call(HashMap<String, User> param) {
                users.putAll(param);
                callback.call(users);
            }
        };
        if (requestIds.size() != 0) {
            new UserDownloader(api, listener, requestIds);
        } else {
            callback.call(users);
        }

    }
}
