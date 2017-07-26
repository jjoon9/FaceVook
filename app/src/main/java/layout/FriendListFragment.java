package layout;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joon.facevook.AcquaintanceLab;
import com.example.joon.facevook.Friend;
import com.example.joon.facevook.FriendLab;
import com.example.joon.facevook.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */


public class FriendListFragment extends Fragment {

    private RecyclerView mFriendRecyclerView;
    private FriendAdapter mAdapter;

    Bitmap bitmap;
    String urlString;
    private Bitmap getImageFromUrl(String u){
        urlString = u;
        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        try{
            mThread.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        return bitmap;
    }


    public FriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        mFriendRecyclerView = (RecyclerView) view.findViewById(R.id.friend_recycler_view);
        mFriendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        FriendLab friendLab = FriendLab.get(getActivity());
        List<Friend> friends = friendLab.getFriends();
        List<Friend> acquaintances = friendLab.getAcquaintances();
        mAdapter = new FriendAdapter(friends, acquaintances);
        mFriendRecyclerView.setAdapter(mAdapter);

    }



    public static FriendListFragment newInstance(){
//        Bundle args = new Bundle();
        FriendListFragment fragment = new FriendListFragment();
//        fragment.setArguments(args);
        fragment.setArguments(new Bundle());
        return fragment;
    }


    private class FriendHeaderHolder extends RecyclerView.ViewHolder{

        private TextView mTitle;

        public FriendHeaderHolder(View itemView){
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.f_header);
        }

        public void bindTitle(String str){
            mTitle.setText(str);
        }

    }

    private class FriendHolder extends RecyclerView.ViewHolder{

        private Friend mFriend;

        private ImageView mProfileImage;
        private TextView mName;
        private TextView mSiriai;
        private Button mAddButton;
        private Button mDeleteButton;

        public FriendHolder(View itemView){
            super(itemView);
            mProfileImage = (ImageView) itemView.findViewById(R.id.f_profile_image);
            mName = (TextView) itemView.findViewById(R.id.f_name);
            mSiriai = (TextView) itemView.findViewById(R.id.f_siriai);
            mAddButton= (Button) itemView.findViewById(R.id.f_add_button);
            mDeleteButton = (Button) itemView.findViewById(R.id.f_delete_button);
        }

        public void bindFriend(Friend friend){
            mFriend = friend;

            mProfileImage.setImageBitmap(getImageFromUrl(mFriend.getProfileImage()));
            mName.setText(mFriend.getName());
            mSiriai.setText("함께 아는 친구 " + mFriend.getSiriai() + "명");
            mAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "친구추가됨!", Toast.LENGTH_SHORT).show();
                }
            });
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "삭제됨!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public class FriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<Friend> mFriends;
        private List<Friend> mAcquaintances;

        private static final int TYPE_HEADER1 = 0;
        private static final int TYPE_HEADER2 = 1;
        private static final int TYPE_FRIEND_ITEM = 2;
        private static final int TYPE_ACQUAINTANCE_ITEM = 3;

        public FriendAdapter(List<Friend> friends){
            mFriends = friends;
        }
        public FriendAdapter(List<Friend> friends, List<Friend> acquaintances){
            mFriends = friends;
            mAcquaintances = acquaintances;
        }

        @Override
        public int getItemCount(){
            return mFriends.size()+2;
        }

        @Override
        public int getItemViewType(int position) { //위치번호로 타입번호구분
            return getType(position);
        }

        private int getType(int position){
            if (position==0){
                return TYPE_HEADER1;
            }
            else if (position==mFriends.size()){
                return TYPE_HEADER2;
            }
            else if (position>0 && position<mFriends.size()){
                return TYPE_FRIEND_ITEM;
            }
            else if(position>mFriends.size())
                return TYPE_ACQUAINTANCE_ITEM;
            else
                return -1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 타입번호별로 뷰홀더생성
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view;
            switch(viewType){
                case TYPE_HEADER1:
                case TYPE_HEADER2:
                    view = layoutInflater.inflate(R.layout.friend, parent, false);
                    return new FriendHeaderHolder(view);

                case TYPE_FRIEND_ITEM:
                case TYPE_ACQUAINTANCE_ITEM:
                    view = layoutInflater.inflate(R.layout.friend, parent, false);
                    return new FriendHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //뷰홀더를 바인드시킬때에는, 위치번호로 결정. 여기서는 인스턴스만으로 구분안됨. 차라리 걍 위치번호로 하는게나음.
            Friend friend;
            FriendHolder friendHolder;
            FriendHeaderHolder friendHeaderHolder;

            switch(getType(position)){
                case TYPE_HEADER1:
                    friendHeaderHolder = (FriendHeaderHolder) holder;
                    friendHeaderHolder.bindTitle("친구 요청");
                    break;

                case TYPE_HEADER2:
                    friendHeaderHolder = (FriendHeaderHolder) holder;
                    friendHeaderHolder.bindTitle("알 수도 있는 사람");
                    break;

                case TYPE_FRIEND_ITEM:
                    friend = mFriends.get(position-1);
                    friendHolder = (FriendHolder) holder;
                    friendHolder.bindFriend(friend);
                    break;

                case TYPE_ACQUAINTANCE_ITEM:
                    friend = mAcquaintances.get(position-1-mFriends.size());
                    friendHolder = (FriendHolder) holder;
                    friendHolder.bindFriend(friend);
                    break;
            }
        }
    }
}


