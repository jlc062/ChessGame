//A portion of this code was taken from http://www.interactivemesh.org/models/jfx3dimporter.html
//The camera motion aspects of this code were taken from https://stackoverflow.com/questions/28731460/javafx-moving-3d-objects-with-mouse-on-a-virtual-plane

package View.View3D.STLParser;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

public class STLParserTest extends Application {

    private PerspectiveCamera camera;

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private final Rotate rotateX = new Rotate(-20, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(-20, Rotate.Y_AXIS);

    private volatile boolean isPicking=false;
    private Point3D vecIni, vecPos;
    private double distance;
    private Sphere s;
    private Scene scene;
    private HBox root;

    @Override
    public void start(Stage stage) throws IOException {

        //initialize the root
        root = new HBox();
        root.setSpacing(300);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        //create three pieces
        MeshView piece1 = createChessPiece("stl/king.stl",Color.RED);
        MeshView piece2 = createChessPiece("stl/king.stl",Color.BLUE);
        MeshView piece3 = createChessPiece("stl/knight.stl",Color.GREEN);

        //move piece1 to create some 3D perspective
        piece1.setTranslateX(100);
        piece1.setTranslateY(100);

        //add the three pieces to the root
        root.getChildren().addAll(piece1,piece2,piece3);

        //initialize the camera
        camera = new PerspectiveCamera(true);
        camera.setVerticalFieldOfView(false);
        camera.setNearClip(0.1);
        camera.setFarClip(100000.0);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -3000));

        //initialize the scene, and set the camera to the scene
        scene = new Scene(root, 1024, 800);
        scene.setCamera(camera);

        //initialize all of the event handlers to deal with moving the camera
        moveCameraEvents();

        //show the scene to the user
        stage.setScene(scene);
        stage.show();

    }

    /**
     * A function to create a MeshView object from an STL file.
     * @param filename the STL file to be parsed and converted to a JavaFX MesView object
     * @param color the color of the MeshView in the viewport
     * @return a MeshView object that will be viewable in the scene
     */
    private MeshView createChessPiece(String filename, Color color) {

        //create a StlMeshImporter object and try parsing with the filename
        StlMeshImporter stlImporter = new StlMeshImporter();
        try {
            stlImporter.read(this.getClass().getResource(filename));
        }
        catch (ImportException e) {
            System.err.println("Error. STL file does not exist.");
            e.printStackTrace();
        }

        //create a MeshView object from the StlMeshImporter
        stlImporter.getImport();
        TriangleMesh mesh = stlImporter.getImport();
        stlImporter.close();
        MeshView meshView = new MeshView(mesh);

        //set the material, general properties, and scale of the mesh
        meshView.setMaterial(new PhongMaterial(color));
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setVisible(true);

        int scale = 3;
        meshView.setScaleX(scale);
        meshView.setScaleY(scale);
        meshView.setScaleZ(scale);

        //rotate the mesh
        meshView.setRotationAxis(Rotate.X_AXIS);
        meshView.setRotate(90);

        return meshView;
    }

    /**
     * A method containing a series of event handlers to move the camera around the scene
     */
    public void moveCameraEvents() {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            PickResult pr = me.getPickResult();
            if(pr!=null && pr.getIntersectedNode() != null && pr.getIntersectedNode() instanceof Sphere){
                distance=pr.getIntersectedDistance();
                s = (Sphere) pr.getIntersectedNode();
                isPicking=true;
                vecIni = unProjectDirection(mousePosX, mousePosY, scene.getWidth(),scene.getHeight());
            }
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            if(isPicking){
                vecPos = unProjectDirection(mousePosX, mousePosY, scene.getWidth(),scene.getHeight());
                Point3D p=vecPos.subtract(vecIni).multiply(distance);
                s.getTransforms().add(new Translate(p.getX(),p.getY(),p.getZ()));
                vecIni=vecPos;
                PickResult pr = me.getPickResult();
                if(pr!=null && pr.getIntersectedNode() != null && pr.getIntersectedNode()==s){
                    distance=pr.getIntersectedDistance();
                } else {
                    isPicking=false;
                }
            } else {
                rotateX.setAngle(rotateX.getAngle()-(mousePosY - mouseOldY));
                rotateY.setAngle(rotateY.getAngle()+(mousePosX - mouseOldX));
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
            }
        });
        scene.setOnMouseReleased((MouseEvent me)->{
            if(isPicking){
                isPicking=false;
            }
        });
    }

    public Point3D unProjectDirection(double sceneX, double sceneY, double sWidth, double sHeight) {
        double tanHFov = Math.tan(Math.toRadians(camera.getFieldOfView()) * 0.5f);
        Point3D vMouse = new Point3D(tanHFov*(2*sceneX/sWidth-1), tanHFov*(2*sceneY/sWidth-sHeight/sWidth), 1);

        Point3D result = localToSceneDirection(vMouse);
        return result.normalize();
    }

    public Point3D localToScene(Point3D pt) {
        Point3D res = camera.localToParentTransformProperty().get().transform(pt);
        if (camera.getParent() != null) {
            res = camera.getParent().localToSceneTransformProperty().get().transform(res);
        }
        return res;
    }

    public Point3D localToSceneDirection(Point3D dir) {
        Point3D res = localToScene(dir);
        return res.subtract(localToScene(new Point3D(0, 0, 0)));
    }

    public static void main(String[] args) {
        launch(args);
    }



}
