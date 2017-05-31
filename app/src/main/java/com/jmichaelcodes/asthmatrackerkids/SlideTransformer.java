package com.jmichaelcodes.asthmatrackerkids;

import android.content.Context;

import com.davidstemmer.screenplay.scene.transformer.TweenTransformer;

/**
 * Created by elevenfifty on 8/28/15.
 */
public class SlideTransformer extends TweenTransformer {
    private static final Params params = new TweenTransformer.Params();

    static {
        params.forwardIn    = R.anim.slide_in_right;
        params.backIn       = R.anim.slide_in_left;
        params.backOut      = R.anim.slide_out_right;
        params.forwardOut   = R.anim.slide_out_left;
    }

    public SlideTransformer(Context context) {
        super(context, params);
    }
}
