package com.projects.pizza;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ImageButton;

import java.util.HashMap;

/**
 * Created by devin on 7/8/16.
 */
public class Util {
    public Util() {}

    private static Integer maxIdUsed = 0;

    public static synchronized Integer newId() { //unique integer amongst the view ids
        return ++maxIdUsed;
    }

    public static synchronized void setMaxId(Integer maxId) { //method to initially set the max id
        maxIdUsed = maxId;
        newId();
    }

    public static void errorMsg(Context context, String message) { //convenience method to show an error msg
        new AlertDialog.Builder(context)
                .setTitle("Alert!")
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static ImageButton createRemoveButton(Context context, Item item) {
        ImageButton imageBtn = new ImageButton(context); //create remove drink button
        imageBtn.setId(item.getRemoveBtnId()); //set id
        imageBtn.setImageResource(R.mipmap.remove_pizza); //set image resource
        imageBtn.setPadding(15, 0, 0, 0); //pad left a bit
        return imageBtn;
    }
}
