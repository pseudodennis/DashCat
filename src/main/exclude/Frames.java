import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;

import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

public class Frames {

    public Frames() {}

    public static void frames() throws FrameGrabber.Exception, IOException {


        FFmpegFrameGrabber g = new FFmpegFrameGrabber("/Users/dtrate/Desktop/DashCat/DubstepCat.mp4");
        g.start();

        for (int i = 0 ; i < 500 ; i += 10) {
            ImageIO.write(g.grab().getBufferedImage(), "png", new File("/Users/dtrate/Desktop/DashCat/frames/" + System.currentTimeMillis() + ".png"));
    }

        g.stop();
    }



}
