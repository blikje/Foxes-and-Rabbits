import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * This class handle's all images used
 *
 * @author Jelko Jerbic, Eduard Hovinga, Martin Poelman
 *
 */
public class ImageClass {
	
	/**
	 * Returns an ImageIcon from a image File
	 *
	 * @param name File name of the image
	 * @param scale Give the size of the Image
	 * @return an ImageIcon
	 */
	public static ImageIcon getImage(String name, int scale)
	{
		ImageIcon tempIcon;
		Image image;
        tempIcon = new ImageIcon(ClassLoader.getSystemResource(name));
		image = tempIcon.getImage();
		tempIcon = scale(image,scale);
		return tempIcon;
	}

	/**
	 * Scales the image to a disired size
	 * @param src Image to scale
	 * @param scale The hight and with in pixels (image will be square)
	 * @return an ImageIcon
	 */
	 private static ImageIcon scale(Image src,int scale){
         //Gooi geheugen leeg

        Runtime r = Runtime.getRuntime();
        r.gc();
        BufferedImage dst;
        int w = scale;
        int h = scale;
        int type = BufferedImage.TYPE_INT_RGB;
        dst = new BufferedImage(w, h, type);
        Graphics2D g2 = dst.createGraphics();
        g2.drawImage(src, 0, 0, w, h,null);
        g2.dispose();
        g2=null;

        return new ImageIcon(dst);

    }
}
