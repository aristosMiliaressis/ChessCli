package ChessCore;

import java.util.HashMap;


public class Rock extends Piece {
	public Rock(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.ROCK);
		setImageFN("rock.png");
		setValue(5);
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
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		moveFile.setRules(rules);

		descriptor = moveFile.new Descriptor();
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		descriptor.setDestCoords(new Board.Coords('A', coords.rank));
		moveFile.setDescriptor(descriptor);
		legalMoves.putAll(moveRank.expandUndefinedMoves());

		return legalMoves;
	}
}
