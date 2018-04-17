package Util.Downloader.Downloaders;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.Message.Message;
import Util.API.Method;
import Util.API.Methods.messages.GetHistoryMethod;
import Util.Downloader.SequentialDownloader;
import Util.Listener;

public class HistorySequentialDownloader extends SequentialDownloader<Message> {
    private final static String TAG = "HistorySeqDl";
    private final Listener<HashMap<String, Message>> onPartialFinishListener;
    private int peer_id;


    public HistorySequentialDownloader(int peer_id,
                                       Listener<HashMap<String, Message>> listener,
                                       Listener<HashMap<String, Message>> onPartialFinishListener) {
        super(listener);
        this.peer_id = peer_id;
        this.onPartialFinishListener = onPartialFinishListener;
        run();
    }

    @Override
    protected GetHistoryMethod getInitialParams() {
        return new GetHistoryMethod(0, 10, peer_id);
    }

    @Override
    protected GetHistoryMethod getParamsForNextRequest(JSONObject response, Method<Message> method) throws JSONException {
        final int count = 10;

        int oldOffset = Integer.valueOf(method.getParams().get("offset"));
        JSONObject oResponse = response.getJSONObject("response");
        int totalMessages = oResponse.getInt("count");
        int receivedMessagesCount = oResponse.getJSONArray("items").length();
        int newOffset = oldOffset + receivedMessagesCount;
        if (newOffset >= totalMessages) {
            return null;
        }
        Log.d(TAG, "Downloading messages" + String.valueOf(newOffset) + " +> " + String.valueOf(count));
        return new GetHistoryMethod(newOffset, count, peer_id);

    }

    @Override
    protected void processResponse(JSONObject response, Method<Message> method) {
        try {
            HashMap<String, Message> newMessages = method.parseResult(response);
            items.putAll(newMessages);

        } catch (JSONException e) {
            Log.e(TAG, "Cannot process response, restarting request");
            requestQueue.add(buildRequest(method));
            e.printStackTrace();
        }

    }

    @Override
    protected void onPartialFinish(boolean isCompleteFinish) {
        //onPartialFinishListener.call();
    }
}
