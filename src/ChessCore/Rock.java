package ChessCore;

import java.util.HashMap;
import java.util.Map;


public class Rock extends Piece {
	public Rock(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.ROCK);
		setImageFN("rock.png");
		setValue(5);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
        System.out.println(getColor().toString() + this + " at " + getCoords().Notation() + " called calculateLegalMoves()");
		HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();

		Move moveRank = new Move();
		Move.Rules rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		moveRank.setRules(rules);

		Move.Descriptor descriptor = moveRank.new Descriptor();
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.setDestCoords(new Board.Coords(getCoords().file, 64));
		moveRank.setDescriptor(descriptor);
		for (Map.Entry<Integer, Move> entry : moveRank.expandUndefinedMoves().entrySet()) {
            Move move = entry.getValue();
            rules = move.getRules();
            descriptor = move.getDescriptor();
            if (rules.check(move)) {
                legalMoves.put(descriptor.getDestCoords().keyable(), move);
            }
        }

		Move moveFile = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		moveFile.setRules(rules);

		descriptor = moveFile.new Descriptor();
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.setDestCoords(new Board.Coords('A', getCoords().rank));
		moveFile.setDescriptor(descriptor);
		for (Map.Entry<Integer, Move> entry : moveFile.expandUndefinedMoves().entrySet()) {
            Move move = entry.getValue();
            rules = move.getRules();
            descriptor = move.getDescriptor();
            if (rules.check(move)) {
                legalMoves.put(descriptor.getDestCoords().keyable(), move);
            }
        }

		return legalMoves;
	}
}
