import org.mp4parser.IsoFile;

import java.io.IOException;

public class GetDuration {
    public static void main(String[] args) throws IOException {
        String filename = GetDuration.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/Users/dtrate/Desktop/DashCat/DubstepCat.mp4";
        IsoFile isoFile = new IsoFile(filename);
        double lengthInSeconds = (double)
                isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
        System.err.println(lengthInSeconds);

    }
}
