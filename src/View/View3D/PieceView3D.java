/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ryan Bailis
 * Section: MWF 11am
 * Instructor: Professor Brian King
 * Date: 11/21/19
 * Time: 11:21 PM
 *
 * Project: csci205FinalProject
 * Package: Import3DMaker
 * Class: ChessPiece3D
 *
 * Description:
 *
 * ****************************************
 */
package View.View3D;


import View.PieceEnum;
import View.PieceView;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class PieceView3D extends PieceView {

    private MeshView pieceMesh;
    private int pieceScale;
    //private boolean isSelected;

    private final int DEFAULT_SCALE = 2;

    /**
     * A constructor to create a MeshView object from an STL file.
     * @param piece the STL file to be parsed and converted to a JavaFX MesView object
     * @param color the color of the MeshView in the viewport
     */
    PieceView3D(PieceEnum piece, Color color) {

        super(piece, color);
        this.pieceScale = DEFAULT_SCALE;
        //this.isSelected = false;

        //create a StlMeshImporter object and try parsing with the filename
        StlMeshImporter stlImporter = new StlMeshImporter();
        try {
            stlImporter.read(this.getClass().getResource(piece.getFileName3D()));
        }
        catch (ImportException e) {
            System.err.println("Error. STL file does not exist.");
            e.printStackTrace();
        }

        //create a MeshView object from the StlMeshImporter
        stlImporter.getImport();
        TriangleMesh mesh = stlImporter.getImport();
        stlImporter.close();

        pieceMesh = new MeshView();
        pieceMesh.setMesh(mesh);

        //set the material, general properties, and scale of the mesh
        pieceMesh.setMaterial(new PhongMaterial(color));
        pieceMesh.setDrawMode(DrawMode.FILL);
        pieceMesh.setVisible(true);

        //scale the pieces appropriately
        pieceMesh.setScaleX(pieceScale);
        pieceMesh.setScaleY(pieceScale);
        pieceMesh.setScaleZ(pieceScale);
    }

    public void selectPiece() {
        //this.isSelected = true;
        pieceMesh.setMaterial(new PhongMaterial(pieceColor.deriveColor(0.5,1,1,1.0)));
    }

    public void deselectPiece() {
        //this.isSelected = false;
        pieceMesh.setMaterial(new PhongMaterial(pieceColor));
    }

//    public boolean isSelected() {
//        return isSelected;
//    }


    public void setPieceScale(int pieceScale) {
        this.pieceScale = pieceScale;
    }

    @Override
    public void setColor(Color newColor){
        this.pieceColor = newColor;
        pieceMesh.setMaterial(new PhongMaterial(newColor));
    }

    public MeshView getPieceMesh() {
        return pieceMesh;
    }
}