package com.bma.counter.clinic.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by montu on 11/7/15.
 */
public class Fonts {

    private static Fonts fonts = null;

  //  private static final String FONT_ARIAL  = "ARIAL.TTF";
    public static final String FONT_MUSEO   = "fonts/Montserrat.ttf";
    public static final String FONT_MUSEO_BOLD   = "fonts/MontserratSemiBold.ttf";
    /*public static final String FONT_MUSEO_1 ="fonts/MUSEOSANSCYRL_1.OTF";
    public static final String FONT_MUSEO_2 ="fonts/MUSEOSANSCYRL_2.OTF";
    public static final String FONT_MUSEO_3 ="fonts/MUSEOSANSCYRL_3.OTF";*/




    public static final int   ARIAL     = 1;
    public static final int   MUSEO     = 2;
    public static final int   MUSEO_1   = 3;
    public static final int   MUSEO_2   = 4;
    public static final int   MUSEO_3   = 5;




    private HashMap<String , Typeface> mHashMap = new HashMap<>();


    private Fonts()
    {

    }


    public static Fonts getInstance(){

        if (fonts == null){
            return fonts = new Fonts();
        }else {
            return fonts;
        }
    }

    public void setTextViewFont(TextView textView , int font){
        textView.setTypeface(getTypeFace(textView.getContext() , font));
    }

    public void setCheckBoxFont(CheckBox checkBox, int font){
        checkBox.setTypeface(getTypeFace(checkBox.getContext(),font));
    }

    public void setEditTextFont(EditText editText , int font){
        editText.setTypeface(getTypeFace(editText.getContext() , font));
    }

    public void setButtonFont(Button button,int font){
        button.setTypeface(getTypeFace(button.getContext(),font));
    }

    public Typeface getTypeFace(Context context , int font){
        Typeface typeface = null;

            switch (font) {

               /* case ARIAL:
                    typeface = mHashMap.get(FONT_ARIAL);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), FONT_ARIAL);
                        mHashMap.put(FONT_ARIAL, typeface);
                    }
                    break;*/

                case MUSEO:
                    typeface = mHashMap.get(FONT_MUSEO);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), FONT_MUSEO);
                        mHashMap.put(FONT_MUSEO, typeface);
                    }
                    break;

                case MUSEO_1:
                    typeface = mHashMap.get(FONT_MUSEO_BOLD);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), FONT_MUSEO_BOLD);
                        mHashMap.put(FONT_MUSEO_BOLD, typeface);
                    }
                    break;

                /*case MUSEO_2:
                    typeface = mHashMap.get(FONT_MUSEO_2);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), FONT_MUSEO_2);
                        mHashMap.put(FONT_ARIAL, typeface);
                    }
                    break;
                case MUSEO_3:
                    typeface = mHashMap.get(FONT_MUSEO_3);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), FONT_MUSEO_3);
                        mHashMap.put(FONT_MUSEO_3, typeface);
                    }
                    break;*/




            }

        return typeface;
    }

    public static Typeface setToolbarTypface(Context context, String fontName){
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontName);
        return  tf;
    }


}
