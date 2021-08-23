
package transformacion3d;
import com.sun.j3d.loaders.Scene;
import java.awt.*;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import java.applet.Applet;
import java.awt.BorderLayout;
import static java.awt.Color.black;
import static java.awt.PageAttributes.MediaType.C;
import java.io.*;
import javax.media.j3d.Alpha;
import javax.media.j3d.Background;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.RotationPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

public class banano extends Applet{
    private SimpleUniverse universe = null;
    private Canvas3D canvas = null;
    private Alpha alpha;
    
    public banano () {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        add("Center", canvas);
        universe = new SimpleUniverse(canvas);
        BranchGroup scene = createSceneGraph();
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
    }
    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere (new Point3d(0,0,0),1000);
        TextureLoader bgtexture = new TextureLoader("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Transformacion 3D\\src\\TEXTURAS\\black-background-leather-wallpaper-preview.jpg",this);
        Background bg = new Background(bgtexture.getImage());
        bg.setApplicationBounds(bounds);objRoot.addChild(bg);
        objRoot.addChild(createBlades());
        objRoot.addChild(createLight());
        return objRoot;
    }
    public BranchGroup createBlades() {
        BranchGroup objRoot = new BranchGroup ();
        TransformGroup tg = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new  Vector3d(-0.6,0.1,-1));
        t3d.setScale(0.14);
        tg.setTransform(t3d);
        TransformGroup vp = new TransformGroup();
        vp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        vp.addChild(createObjLoad("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Transformacion 3D\\src\\TEXTURAS\\21942_Banana_v1.obj"));
        Alpha Alpha = new Alpha (-1,20000);
        Transform3D axis = new Transform3D();
        axis.setRotation(new AxisAngle4f(1.3f, 8.0f, 7.0f, 8.0f));
        RotationInterpolator rotator = new RotationInterpolator(alpha, vp, axis, 0.0f, (float) Math.PI * (6.0f));
        BoundingSphere bounds = new BoundingSphere(new  Point3d(0.0, 0.0,0.0), 2000.0);
        rotator.setSchedulingBounds(bounds);
        vp.addChild(rotator);
        tg.addChild(vp);
        objRoot.addChild(tg);
        objRoot.addChild(createLight());
        objRoot.compile();
        return objRoot;
    }
     private BranchGroup createObjLoad(String filename) {
    BranchGroup objRoot = new BranchGroup();
    TransformGroup tg = new TransformGroup ();
    ObjectFile loader = new ObjectFile();
    Scene  s = null;
 
    File file = new java.io.File(filename);
    try {
    s = loader.load(file.toURI().toURL());
}   catch (Exception e) {
    System.err.println(e);
    System.exit (1);
}
    tg.addChild(s.getSceneGroup());
    objRoot.addChild(tg);
    objRoot.compile();
    return objRoot;
}
   private Light createLight () {
  DirectionalLight light = new DirectionalLight(true, new Color3f(1.0f, 1.0f, 1.0f), new Vector3f(0-3f,0.2f, -1.0f));
light.setInfluencingBounds(new BoundingSphere(new Point3d (),10000.0));
return light;
}
public static void main (String[] args) {
 banano applet = new banano() ;
Frame frame = new MainFrame(applet, 600,600);
 }

}
