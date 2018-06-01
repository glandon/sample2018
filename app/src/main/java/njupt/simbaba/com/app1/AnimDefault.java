package njupt.simbaba.com.app1;

import android.animation.Animator;
import android.view.animation.Animation;


public class AnimDefault implements Animator.AnimatorListener, Animation.AnimationListener {
    private Runnable onAnimationEnd;

    AnimDefault(Runnable run) {
        onAnimationEnd = run;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        onAnimationEnd.run();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        onAnimationEnd.run();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
