package com.example.joon.facevook;

import java.util.UUID;

/**
 * Created by Joon on 2017-07-22.
 */

public class Friend {
    private String mProfileImage;
    private String mName;
    private int mSiriai;
    private UUID mId;

    public UUID getId() {
        return mId;
    }

    public Friend(String profileImage, String name, int siriai) {
        mProfileImage = profileImage;
        mName = name;
        mSiriai = siriai;
        mId = UUID.randomUUID();
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public String getName() {
        return mName;
    }

    public int getSiriai() {
        return mSiriai;
    }
}
