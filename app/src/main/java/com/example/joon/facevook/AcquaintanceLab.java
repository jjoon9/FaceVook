package com.example.joon.facevook;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Joon on 2017-07-23.
 */

public class AcquaintanceLab {


    private static AcquaintanceLab sAcquaintanceLab;
    private List<Friend> mAcquaintances;


    private Friend getExmapleAcquaintance(){
        String profileImage="https://support.plymouth.edu/kb_images/Yammer/default.jpeg";
        String name="SangHyub Lee";
        int siriai=143;
        return new Friend(profileImage,name,siriai);
    }

    public AcquaintanceLab(Context context) {
        mAcquaintances = new ArrayList<>();
        mAcquaintances.add(getExmapleAcquaintance());
        mAcquaintances.add(getExmapleAcquaintance());
        mAcquaintances.add(getExmapleAcquaintance());
        mAcquaintances.add(getExmapleAcquaintance());
        mAcquaintances.add(getExmapleAcquaintance());



    }

    public static AcquaintanceLab get(Context context){
        if(sAcquaintanceLab == null){
            sAcquaintanceLab = new AcquaintanceLab(context);
        }
        return sAcquaintanceLab;
    }

    public List<Friend> getAcquaintances(){
        return mAcquaintances;
    }


    public Friend getAcquaintance(UUID id){
        for (Friend acquaintance : mAcquaintances){
            if (acquaintance.getId().equals(id)){
                return acquaintance;
            }
        }
        return null;
    }


}

