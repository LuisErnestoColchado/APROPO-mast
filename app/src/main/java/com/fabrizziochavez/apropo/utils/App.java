package com.fabrizziochavez.apropo.utils;

import android.app.Application;
import android.graphics.Typeface;

import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

/**
 * Created by BLiveInHack on 18-01-2016.
 */
public class App extends Application {
    private static TypefaceCollection QuicksandTypeface;

    public static TypefaceCollection getRoboto_bold() {
        return roboto_bold;
    }

    public static TypefaceCollection getRoboto_regular() {
        return roboto_regular;
    }

    private static TypefaceCollection roboto_bold;
    private static TypefaceCollection roboto_regular;

    public static TypefaceCollection getNew_Cicle() {
        return new_Cicle;
    }

    private static TypefaceCollection new_Cicle;

    public static TypefaceCollection getCalibri() {
        return Calibri;
    }

    private static TypefaceCollection Calibri;

    public static TypefaceCollection getBlackJar() {
        return blackJar;
    }

    private static TypefaceCollection blackJar;

    public static TypefaceCollection getHelveticaNeueArabic() {
        return helveticaNeueArabic;
    }

    private static TypefaceCollection helveticaNeueArabic;

    public static TypefaceCollection getOpenSansTypeFace() {
        return OpenSansTypeFace;
    }

    public static TypefaceCollection getQuicksandTypeface() {
        return QuicksandTypeface;
    }

    private static TypefaceCollection OpenSansTypeFace;

    @Override
    public void onCreate() {
        super.onCreate();
        QuicksandTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(),
                        "Quicksand-Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(),
                        "Quicksand-Bold.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "Quicksand-Light.ttf"))
                .create();

        OpenSansTypeFace = new TypefaceCollection.Builder()
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "OpenSans-Light.ttf"))
                .create();

        helveticaNeueArabic = new TypefaceCollection.Builder()
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "HelveticaNeueLTArabic-Bold.ttf"))
                .create();

        blackJar = new TypefaceCollection.Builder()
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "blackjar.TTF"))
                .create();

        Calibri = new TypefaceCollection.Builder()
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "Calibri.ttf"))
                .create();
        new_Cicle = new TypefaceCollection.Builder()
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "New_Cicle_Fina.ttf"))
                .create();

        roboto_bold = new TypefaceCollection.Builder()
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "Roboto-Bold.ttf"))
                .create();

        roboto_regular = new TypefaceCollection.Builder()
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(),
                        "Roboto-Regular.ttf"))
                .create();
        TypefaceHelper.init(QuicksandTypeface);
    }
}
