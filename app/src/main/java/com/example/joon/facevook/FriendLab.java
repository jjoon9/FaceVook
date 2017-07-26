package com.example.joon.facevook;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Joon on 2017-07-23.
 */

public class FriendLab {


    private static FriendLab sFriendLab;
    private List<Friend> mFriends;
    private List<Friend> mAcquaintances;

    private Friend getExmapleFriend(){
        String profileImage="https://support.plymouth.edu/kb_images/Yammer/default.jpeg";
        String name="Hanmo Koo";
        int siriai=132;
        return new Friend(profileImage,name,siriai);
    }
    private Friend getExmapleAcquaintance(){
        String profileImage="https://support.plymouth.edu/kb_images/Yammer/default.jpeg";
        String name="SangHyub Lee";
        int siriai=143;
        return new Friend(profileImage,name,siriai);
    }

    public FriendLab(Context context) {
                mFriends = new ArrayList<>();
                mFriends.add(getExmapleFriend());
                mFriends.add(getExmapleFriend());
                mFriends.add(getExmapleFriend());

                mAcquaintances = new ArrayList<>();
                mAcquaintances.add(getExmapleAcquaintance());
                mAcquaintances.add(getExmapleAcquaintance());
                mAcquaintances.add(getExmapleAcquaintance());
    }

    public static FriendLab get(Context context){
        if(sFriendLab == null){
            sFriendLab = new FriendLab(context);
        }
        return sFriendLab;
    }

    public List<Friend> getFriends(){
        return mFriends;
    }
    public List<Friend> getAcquaintances(){
        return mAcquaintances;
    }


    public Friend getFriend(UUID id){
        for (Friend friend : mFriends){
            if (friend.getId().equals(id)){
                return friend;
            }
        }
        return null;
    }
    public Friend getAcquaintace(UUID id){
        for (Friend friend : mAcquaintances){
            if (friend.getId().equals(id)){
                return friend;
            }
        }
        return null;
    }

}

