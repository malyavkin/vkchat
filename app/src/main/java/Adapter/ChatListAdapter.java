package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import Persistence.Entities.Dialog.Dialog;
import co.lesha.vkchat.R;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    Context context;
    ArrayList<Dialog> items;

    // OnBottomReachedListener -- наш класс слушателя события, когда rv достигает дна
    private OnBottomReachedListener onBottomReachedListener;

    public ChatListAdapter(Context context, ArrayList<Dialog> items) {
        this.items = items;
        this.context = context;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    /**
     * Создание нового вьюхолдера
     * LayoutInflater создает вьюху из layout
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_list_item,parent,false)
        );
    }

    /**
     * Этот метод будет вызван, когда вьюхолдер будет нужен для новых данных
     * @param holder вьюхолдер
     * @param position позиция в списке
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dialog item = items.get(position);
        holder.chatListItem_title.setText(item.getTitle());
        holder.chatListItem_lastMessage.setText(item.getLastMessage());

        if (position == items.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatListItem_title, chatListItem_lastMessage;

        /**
         * ViewHolder занимается склейкой нашего RecyclerView и его дочерних элементов.
         * Здесь мы достаем из элемента все нужные вьюхи, которые надо заполнить.
         * Вьюха будет переиспользоваться.
         * @param itemView вью элемента.
         */
        ViewHolder(View itemView) {
            super(itemView);
            chatListItem_title = itemView.findViewById(R.id.chatListItem_title);
            chatListItem_lastMessage = itemView.findViewById(R.id.chatListItem_lastMessage);
        }
    }
}