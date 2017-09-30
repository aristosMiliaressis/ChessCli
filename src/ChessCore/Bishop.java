package ChessCore;

import java.util.HashMap;


public class Bishop extends Piece {
	public Bishop(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.BISHOP);
		setImageFN("bishop.png");
		setValue(3);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
        Board.Coords coords = this.getCoords();
        Color color = this.getColor();
        Move.Rules rules;
        Move.Descriptor descriptor;
        
		Move moveDiag = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		moveDiag.setRules(rules);

		descriptor = moveDiag.new Descriptor();
		descriptor.playingColor(color);
		descriptor.setSrcCoords(coords);
		descriptor.setDestCoords(new Board.Coords('A', 64));
		moveDiag.setDescriptor(descriptor);

		return moveDiag.expandUndefinedMoves();
	}
}
