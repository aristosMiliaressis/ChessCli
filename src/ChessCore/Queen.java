package ChessCore;

import java.util.HashMap;


public class Queen extends Piece {
	public Queen(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.QUEEN);
		setImageFN("queen.png");
		setValue(9);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
	    HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();
        Board.Coords coords = this.getCoords();
        Color color = this.getColor();
        Move.Rules rules;
        Move.Descriptor descriptor;
        
		Move moveRank = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		moveRank.setRules(rules);

		descriptor = moveRank.new Descriptor();
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		descriptor.setDestCoords(new Board.Coords(coords.file, 64));
		moveRank.setDescriptor(descriptor);
		legalMoves.putAll(moveRank.expandUndefinedMoves());
		

		Move moveFile = new Move();
		descriptor = moveFile.new Descriptor();
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		descriptor.setDestCoords(new Board.Coords('A', coords.rank));
		moveFile.setDescriptor(descriptor);

		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		moveFile.setRules(rules);
		legalMoves.putAll(moveFile.expandUndefinedMoves());


		Move moveDiag = new Move();
		descriptor = moveDiag.new Descriptor();
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		descriptor.setDestCoords(new Board.Coords('A', 64));
		moveDiag.setDescriptor(descriptor);

		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		moveDiag.setRules(rules);
		legalMoves.putAll(moveDiag.expandUndefinedMoves());

		return legalMoves;
	}
}
