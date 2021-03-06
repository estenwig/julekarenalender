package no.jervell.view.animation.impl;

import com.github.julekarenalender.log.Logger$;
import no.jervell.view.animation.Animation;

/**
 * @author Arne C. Jervell (arne@jervell.no)
 */
public class AnimationLoop extends Thread {
    private static final Logger$ logger = Logger$.MODULE$;
    private volatile boolean keepRunning;
    private DefaultTimer timer;
    private Animation[] animations;

    public AnimationLoop() {
        timer = new DefaultTimer(50);
        keepRunning = true;
        setDaemon(true);
    }

    public void setAnimations(Animation... animations) {
        this.animations = animations;
    }

    public void end() {
        keepRunning = false;
    }

    @Override
    public void run() {
        try {
            initAnimations();
            while (keepRunning) {
                timer.waitForNextFrame();
                moveAnimations();
            }
        } catch (InterruptedException e) {
            logger.info("Breaking out of loop. Reason: " + e);
        }
        logger.debug("Loop stopped.");
    }

    private void initAnimations() {
        for (Animation animation : animations) {
            animation.init(timer);
        }
    }

    private void moveAnimations() {
        for (Animation animation : animations) {
            animation.move(timer);
        }
    }
}
