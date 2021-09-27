package View;

public enum PieceEnum {
    ROOK("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Chess_tile_rl-whitebg.svg/600px-Chess_tile_rl-whitebg.svg.png","stlModels/rook.stl"),
    KNIGHT("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Chess_clt45.svg/600px-Chess_clt45.svg.png","stlModels/knight.stl"),
    BISHOP("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Chess_blt45.svg/600px-Chess_blt45.svg.png","stlModels/bishop.stl"),
    KING("https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Chess_klt45.svg/600px-Chess_klt45.svg.png","stlModels/king.stl"),
    QUEEN("https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Chess_qlt45.svg/600px-Chess_qlt45.svg.png","stlModels/queen.stl"),
    PAWN("https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Chess_plt45.svg/600px-Chess_plt45.svg.png","stlModels/pawn.stl");

    private final String fileName2D;
    private final String fileName3D;

    PieceEnum(String filename2D, String fileName3D) {
        this.fileName2D = filename2D;
        this.fileName3D = fileName3D;
    }

    public String getFileName2D() {
        return fileName2D;
    }

    public String getFileName3D(){
        return fileName3D;
    }
}

