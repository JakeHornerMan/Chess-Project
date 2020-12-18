import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
/*
	This class can be used as a starting point for creating your Chess game project. The only piece that
	has been coded is a white pawn...a lot done, more to do!
*/

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
	int startX;
	int startY;
	int initialX;
	int initialY;

    private int landingX;
    private int landingY;
    private int xMovement;
    private int yMovement;

    private String pieceName;
    private Boolean InTheWay;
    private Boolean success;
    private Boolean progression;
    private int turnNum =2;
    private Boolean whiteTurn;

    static int mode;

	Boolean validMove = false;
	JPanel panels;
	JLabel pieces;

    AIAgent agent;
    Stack temporary;
    Boolean agentwins = false;


    public ChessProject(){

        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
            else
                square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
        }

        // Setting up the Initial Chess board.
		for(int i=8;i < 16; i++){
       		pieces = new JLabel( new ImageIcon("WhitePawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);
		}
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(0);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(1);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(6);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishop.png") );
		panels = (JPanel)chessBoard.getComponent(2);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishop.png") );
		panels = (JPanel)chessBoard.getComponent(5);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKing.png") );
		panels = (JPanel)chessBoard.getComponent(3);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
		panels = (JPanel)chessBoard.getComponent(4);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(7);
	    panels.add(pieces);
		for(int i=48;i < 56; i++){
       		pieces = new JLabel( new ImageIcon("BlackPawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);
		}
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(56);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(57);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(62);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishop.png") );
		panels = (JPanel)chessBoard.getComponent(58);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishop.png") );
		panels = (JPanel)chessBoard.getComponent(61);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKing.png") );
		panels = (JPanel)chessBoard.getComponent(59);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackQueen.png") );
		panels = (JPanel)chessBoard.getComponent(60);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(63);
	    panels.add(pieces);

        agent = new AIAgent();
        agentwins = false;
        temporary = new Stack();
    }

	/*
		This method checks if there is a piece present on a particular square.
	*/
	private Boolean piecePresent(int x, int y){
		Component c = chessBoard.findComponentAt(x, y);
		if(c instanceof JPanel){
			return false;
		}
		else{
			return true;
		}
	}

    private String getPieceName(int x, int y){
        Component c1 = chessBoard.findComponentAt(x, y);
        if(c1 instanceof JPanel){
            return "empty";
        }
        else if(c1 instanceof JLabel){
            JLabel awaitingPiece = (JLabel)c1;
            String tmp1 = awaitingPiece.getIcon().toString();
            return tmp1;
        }
        else{
            return "empty";
        }
    }
	/*
		This is a method to check if a piece is a Black piece.
	*/
	private Boolean checkWhiteOponent(int landingX, int landingY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(landingX, landingY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if(((tmp1.contains("Black")))){
			oponent = true;
		}
		else{
			oponent = false;
		}
		return oponent;
	}

    private Boolean checkBlackOponent(int landingX,int landingY){
        Boolean oponent;
        Component c1 = chessBoard.findComponentAt(landingX,landingY);
        JLabel awaitingPiece = (JLabel)c1;
        String tmp1 = awaitingPiece.getIcon().toString();

        if (((tmp1.contains("White")))) {
            oponent = true;
        } else {
            oponent = false;
        }
        return oponent;
    }

    private Boolean kingCheckKing(int landingX, int landingY){
	    Boolean oponent;
        Component c1 = chessBoard.findComponentAt(landingX, landingY);
        if(c1==null){
            oponent = false;
        }

        JLabel awaitingPiece = (JLabel)c1;
        String tmp1 = awaitingPiece.getIcon().toString();
        if(((tmp1.contains("King")))){
            oponent = true;
        }
        else{
            oponent = false;
        }

        return oponent;
    }

    private Boolean WhiteKing(int landingX, int landingY){
        Boolean oponent;
        Component c1 = chessBoard.findComponentAt(landingX, landingY);
        if(c1==null){
            oponent = false;
        }

        JLabel awaitingPiece = (JLabel)c1;
        String tmp1 = awaitingPiece.getIcon().toString();
        if(((tmp1.contains("WhiteKing")))){
            oponent = true;
        }
        else{
            oponent = false;
        }

        return oponent;
    }
    private Boolean BlackKing(int landingX, int landingY){
        Boolean oponent;
        Component c1 = chessBoard.findComponentAt(landingX, landingY);
        if(c1==null){
            oponent = false;
        }

        JLabel awaitingPiece = (JLabel)c1;
        String tmp1 = awaitingPiece.getIcon().toString();
        if(((tmp1.contains("BlackKing")))){
            oponent = true;
        }
        else{
            oponent = false;
        }

        return oponent;
    }

	/*
		This method is called when we press the Mouse. So we need to find out what piece we have
		selected. We may also not have selected a piece!
	*/
    public void mousePressed(MouseEvent e){
        if(SwingUtilities.isRightMouseButton(e)){
            validMove=false;
        }

        //makeAIMove();

        chessPiece = null;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel) {
            return;
        }
        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel)c;
    		initialX = e.getX();
    		initialY = e.getY();
    		startX = (e.getX()/75);
    		startY = (e.getY()/75);
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

 	/*
		This method is used when the Mouse is released...we need to make sure the move was valid before
		putting the piece back on the board.
	*/
    public void mouseReleased(MouseEvent e) {

        if(chessPiece == null) return;

        chessPiece.setVisible(false);

        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
        String tmp = chessPiece.getIcon().toString();
        pieceName = tmp.substring(0, (tmp.length()-4));

        if (turnNum % 2 == 0) {
            whiteTurn = false;
        }
        else {
            whiteTurn = true;
            //makeAIMove();
        }

        validMove = false;
        success =false;
        progression =false;
        InTheWay = false;

        landingX = (e.getX()/75);
        landingY = (e.getY()/75);
        xMovement = Math.abs((e.getX()/75)-startX);
        yMovement = Math.abs((e.getY()/75)-startY);

        if(whiteTurn == true && pieceName.contains("White")) {
            switch (pieceName) {
                case "WhitePawn":
                    moveWPawn();
                    break;
                case "WhiteKnight":
                    moveKnight();
                    break;
                case "WhiteBishop":
                    moveBishop();
                    break;
                case "WhiteRook":
                    moveRook();
                    break;
                case "WhiteQueen":
                    moveQueen();
                    break;
                case "WhiteKing":
                    moveKing();
                    break;
                default:
                    validMove = false;
                    break;
            }
        }else if (whiteTurn == true && pieceName.contains("Black")){
            validMove=false;
        }
        else if (whiteTurn == false && pieceName.contains("Black")){
            switch (pieceName) {
                case "BlackPawn":
                    moveBPawn();
                    break;
                case "BlackKnight":
                    moveKnight();
                    break;
                case "BlackBishop":
                    moveBishop();
                    break;
                case "BlackRook":
                    moveRook();
                    break;
                case "BlackQueen":
                    moveQueen();
                    break;
                case "BlackKing":
                    moveKing();
                    break;
                default:
                    validMove = false;
                    break;
            }
        }
        else if (whiteTurn == false && pieceName.contains("White")){
            validMove=false;
        }


        if(landingX==startX&&landingY==startY){
            validMove=false;
            turnNum--;
        }

        if(!validMove){
            int location=0;
            if(startY ==0){
                location = startX;
            }
            else{
                location  = (startY*8)+startX;
            }
            String pieceLocation = pieceName+".png";
            pieces = new JLabel( new ImageIcon(pieceLocation) );
            panels = (JPanel)chessBoard.getComponent(location);
            panels.add(pieces);
        }
        else{

            if(progression){
                turnNum++;
                int location = 0 + (e.getX()/75);
                if (c instanceof JLabel){
                Container parent = c.getParent();
                parent.remove(0);
                pieces = new JLabel(new ImageIcon("BlackQueen.png"));
                parent = (JPanel)chessBoard.getComponent(location);
                parent.add(pieces);
                }
                else{
                    Container parent = (Container)c;
                    pieces = new JLabel( new ImageIcon("BlackQueen.png") );
                    parent = (JPanel)chessBoard.getComponent(location);
                    parent.add(pieces);
                }
            }
            else if(success){
                turnNum++;
                int location = 56 + (e.getX()/75);
                if (c instanceof JLabel){
                    Container parent = c.getParent();
                    parent.remove(0);
                    pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
                    parent = (JPanel)chessBoard.getComponent(location);
                    parent.add(pieces);
                }
                else{
                    Container parent = (Container)c;
                    pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
                    parent = (JPanel)chessBoard.getComponent(location);
                    parent.add(pieces);
                }
            }
            else{
                turnNum++;
                if (c instanceof JLabel){
                    Container parent = c.getParent();
                    parent.remove(0);
                    parent.add( chessPiece );
                }
                else {
                    Container parent = (Container)c;
                    parent.add( chessPiece );
                }
                chessPiece.setVisible(true);

                makeAIMove();
            }
        }
		//loggercode
        if (validMove== true) {
            System.out.println("-------------------------------------------------");
            System.out.println("This piece:" + pieceName);
            System.out.println("Starting position:" + "(" + startX + "," + startY + ")");
            System.out.println("xMovement is:" + xMovement);
            System.out.println("yMovement is:" + yMovement);
            System.out.println("Landing position" + "(" + landingX + "," + landingY + ")");
            System.out.println("-------------------------------------------------");
        }
        //makeAIMove();
    }

    //Get Piece Name for AI

    private void makeAIMove(){
        if (mode==0){
            return;
        }
        else if (mode==1) {
            turnNum++;
            resetBorders();
            layeredPane.validate();
            layeredPane.repaint();
            Stack white = findWhitePieces();
            Stack black = findBlackPieces();
            Stack completeMoves = new Stack();
            Move tmp;
            while (!white.empty()) {
                Square s = (Square) white.pop();
                String tmpString = s.getName();
                Stack tmpMoves = new Stack();
                Stack temporary = new Stack();

                if (tmpString.contains("Knight")) {
                    tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getName());
                } else if (tmpString.contains("Bishop")) {
                    tmpMoves = getBishopMoves(s.getXC(), s.getYC(), s.getName());
                    //tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getName());
                } else if (tmpString.contains("Pawn")) {
                    tmpMoves = getWhitePawnSquares(s.getXC(), s.getYC(), s.getName());
                    //tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getName());
                } else if (tmpString.contains("Rook")) {
                    tmpMoves = getRookMoves(s.getXC(), s.getYC(), s.getName());
                    //tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getName());
                } else if (tmpString.contains("Queen")) {
                    tmpMoves = getQueenMoves(s.getXC(), s.getYC(), s.getName());
                    //tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getName());
                } else if (tmpString.contains("King")) {
                    tmpMoves = getKingSquares(s.getXC(), s.getYC(), s.getName());
                    //tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getName());
                }

                while (!tmpMoves.empty()) {
                    tmp = (Move) tmpMoves.pop();
                    completeMoves.push(tmp);
                }
            }
            temporary = (Stack) completeMoves.clone();
            getLandingSquares(temporary);
            printStack(temporary);

            if (completeMoves.size() == 0) {

                JOptionPane.showMessageDialog(null, "Cogratulations, you have placed the AI component in a Stale Mate Position");
                //System.exit(0);

            } else {

                System.out.println("=============================================================");
                Stack testing = new Stack();
                while (!completeMoves.empty()) {
                    Move tmpMove = (Move) completeMoves.pop();
                    Square s1 = (Square) tmpMove.getStart();
                    Square s2 = (Square) tmpMove.getLanding();
                    System.out.println("The " + s1.getName() + " can move from (" + s1.getXC() + ", " + s1.getYC() + ") to the following square: (" + s2.getXC() + ", " + s2.getYC() + ")");
                    testing.push(tmpMove);
                }
                System.out.println("=============================================================");
                Border redBorder = BorderFactory.createLineBorder(Color.RED, 3);
                Move selectedMove = agent.randomMove(testing);
                Square startingPoint = (Square) selectedMove.getStart();
                Square landingPoint = (Square) selectedMove.getLanding();
                int startX1 = (startingPoint.getXC() * 75) + 20;
                int startY1 = (startingPoint.getYC() * 75) + 20;
                int landingX1 = (landingPoint.getXC() * 75) + 20;
                int landingY1 = (landingPoint.getYC() * 75) + 20;
                System.out.println("-------- Move " + startingPoint.getName() + " (" + startingPoint.getXC() + ", " + startingPoint.getYC() + ") to (" + landingPoint.getXC() + ", " + landingPoint.getYC() + ")");

                Component c = (JLabel) chessBoard.findComponentAt(startX1, startY1);
                Container parent = c.getParent();
                parent.remove(c);
                int panelID = (startingPoint.getYC() * 8) + startingPoint.getXC();
                panels = (JPanel) chessBoard.getComponent(panelID);
                panels.setBorder(redBorder);
                parent.validate();

                Component l = chessBoard.findComponentAt(landingX1, landingY1);
                if (l instanceof JLabel) {
                    Container parentlanding = l.getParent();
                    JLabel awaitingName = (JLabel) l;
                    String agentCaptured = awaitingName.getIcon().toString();
                    if (agentCaptured.contains("King")) {
                        agentwins = true;
                    }
                    parentlanding.remove(l);
                    parentlanding.validate();
                    pieces = new JLabel(new ImageIcon(startingPoint.getName() + ".png"));
                    int landingPanelID = (landingPoint.getYC() * 8) + landingPoint.getXC();
                    panels = (JPanel) chessBoard.getComponent(landingPanelID);
                    panels.add(pieces);
                    panels.setBorder(redBorder);
                    layeredPane.validate();
                    layeredPane.repaint();

                    if (agentwins) {
                        JOptionPane.showMessageDialog(null, "The AI Agent has won!");
                        System.exit(0);
                    }
                } else {
                    pieces = new JLabel(new ImageIcon(startingPoint.getName() + ".png"));
                    int landingPanelID = (landingPoint.getYC() * 8) + landingPoint.getXC();
                    panels = (JPanel) chessBoard.getComponent(landingPanelID);
                    panels.add(pieces);
                    panels.setBorder(redBorder);
                    layeredPane.validate();
                    layeredPane.repaint();
                }
                //white2Move = false;

            }
        }
    }

    private void printStack(Stack input){
        Move m;
        Square s, l;
        while(!input.empty()){
            m = (Move)input.pop();
            s = (Square)m.getStart();
            l = (Square)m.getLanding();
            System.out.println("The possible move that was found is : ("+s.getXC()+" , "+s.getYC()+"), landing at ("+l.getXC()+" , "+l.getYC()+")");
        }
    }
    //ALL POSSIBLE MOVES
    //White Pawn
    private Stack getWhitePawnSquares(int x, int y, String piece) {
        Stack moves = new Stack();

        Square startingSquare = new Square(x, y, piece);

        Move validM, validM2, validM3, validM4;

        for (int i = 1; i <= 2; i++) {
            int tmpx = x;
            int tmpy = y + i;

            if (!(tmpy > 7)) {
                if (y == 1) {
                    Square tmp = new Square(tmpx, tmpy, piece);
                    validM = new Move(startingSquare, tmp);

                    if (!piecePresent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20)))) {
                        moves.push(validM);
                    }
                }
            }
        }

        for (int i = 1; i < 2; i++) {
            int tmpx = x;
            int tmpy = y + i;

            if (!(tmpy > 7)) {
                Square tmp = new Square(tmpx, tmpy, piece);
                validM2 = new Move(startingSquare, tmp);

                if (!piecePresent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20)))) {
                    moves.push(validM2);
                }
            }
        }

        for (int i = 1; i < 2; i++) {
            int tmpx = x - i;
            int tmpy = y + i;

            if (!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
                Square tmp = new Square(tmpx, tmpy, piece);
                validM3 = new Move(startingSquare, tmp);

                if (piecePresent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20)))) {
                    if (checkWhiteOponent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20)))) {
                        moves.push(validM3);
                    }
                }
            }
        }
        return moves;
    }

    //King Moves
    private Stack getKingSquares(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Move validM, validM2, validM3, validM4;
        int tmpx1 = x+1;
        int tmpx2 = x-1;
        int tmpy1 = y+1;
        int tmpy2 = y-1;

        if(!((tmpx1 > 7))){
            Square tmp = new Square(tmpx1, y, piece);
            Square tmp1 = new Square(tmpx1, tmpy1, piece);
            Square tmp2 = new Square(tmpx1, tmpy2, piece);
            if(checkSurroundingSquares(tmp)){
                validM = new Move(startingSquare, tmp);
                if(!piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
                    moves.push(validM);
                }
                else{
                    if(checkWhiteOponent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
                        moves.push(validM);
                    }
                }
            }
            if(!(tmpy1 > 7)){
                if(checkSurroundingSquares(tmp1)){
                    validM2 = new Move(startingSquare, tmp1);
                    if(!piecePresent(((tmp1.getXC()*75)+20), (((tmp1.getYC()*75)+20)))){
                        moves.push(validM2);
                    }
                    else{
                        if(checkWhiteOponent(((tmp1.getXC()*75)+20), (((tmp1.getYC()*75)+20)))){
                            moves.push(validM2);
                        }
                    }
                }
            }
            if(!(tmpy2 < 0)){
                if(checkSurroundingSquares(tmp2)){
                    validM3 = new Move(startingSquare, tmp2);
                    if(!piecePresent(((tmp2.getXC()*75)+20), (((tmp2.getYC()*75)+20)))){
                        moves.push(validM3);
                    }
                    else{
                        System.out.println("The values that we are going to be looking at are : "+((tmp2.getXC()*75)+20)+" and the y value is : "+((tmp2.getYC()*75)+20));
                        if(checkWhiteOponent(((tmp2.getXC()*75)+20), (((tmp2.getYC()*75)+20)))){
                            moves.push(validM3);
                        }
                    }
                }
            }
        }
        if(!((tmpx2 < 0))){
            Square tmp3 = new Square(tmpx2, y, piece);
            Square tmp4 = new Square(tmpx2, tmpy1, piece);
            Square tmp5 = new Square(tmpx2, tmpy2, piece);
            if(checkSurroundingSquares(tmp3)){
                validM = new Move(startingSquare, tmp3);
                if(!piecePresent(((tmp3.getXC()*75)+20), (((tmp3.getYC()*75)+20)))){
                    moves.push(validM);
                }
                else{
                    if(checkWhiteOponent(((tmp3.getXC()*75)+20), (((tmp3.getYC()*75)+20)))){
                        moves.push(validM);
                    }
                }
            }
            if(!(tmpy1 > 7)){
                if(checkSurroundingSquares(tmp4)){
                    validM2 = new Move(startingSquare, tmp4);
                    if(!piecePresent(((tmp4.getXC()*75)+20), (((tmp4.getYC()*75)+20)))){
                        moves.push(validM2);
                    }
                    else{
                        if(checkWhiteOponent(((tmp4.getXC()*75)+20), (((tmp4.getYC()*75)+20)))){
                            moves.push(validM2);
                        }
                    }
                }
            }
            if(!(tmpy2 < 0)){
                if(checkSurroundingSquares(tmp5)){
                    validM3 = new Move(startingSquare, tmp5);
                    if(!piecePresent(((tmp5.getXC()*75)+20), (((tmp5.getYC()*75)+20)))){
                        moves.push(validM3);
                    }
                    else{
                        if(checkWhiteOponent(((tmp5.getXC()*75)+20), (((tmp5.getYC()*75)+20)))){
                            moves.push(validM3);
                        }
                    }
                }
            }
        }
        Square tmp7 = new Square(x, tmpy1, piece);
        Square tmp8 = new Square(x, tmpy2, piece);
        if(!(tmpy1 > 7)){
            if(checkSurroundingSquares(tmp7)){
                validM2 = new Move(startingSquare, tmp7);
                if(!piecePresent(((tmp7.getXC()*75)+20), (((tmp7.getYC()*75)+20)))){
                    moves.push(validM2);
                }
                else{
                    if(checkWhiteOponent(((tmp7.getXC()*75)+20), (((tmp7.getYC()*75)+20)))){
                        moves.push(validM2);
                    }
                }
            }
        }
        if(!(tmpy2 < 0)){
            if(checkSurroundingSquares(tmp8)){
                validM3 = new Move(startingSquare, tmp8);
                if(!piecePresent(((tmp8.getXC()*75)+20), (((tmp8.getYC()*75)+20)))){
                    moves.push(validM3);
                }
                else{
                    if(checkWhiteOponent(((tmp8.getXC()*75)+20), (((tmp8.getYC()*75)+20)))){
                        moves.push(validM3);
                    }
                }
            }
        }
        return moves;
    }
    //Queen Moves
    private Stack getQueenMoves(int x, int y, String piece){
        Stack completeMoves = new Stack();
        Stack tmpMoves = new Stack();
        Move tmp;

        tmpMoves = getRookMoves(x, y, piece);
        while(!tmpMoves.empty()){
            tmp = (Move)tmpMoves.pop();
            completeMoves.push(tmp);
        }
        tmpMoves = getBishopMoves(x, y, piece);
        while(!tmpMoves.empty()){
            tmp = (Move)tmpMoves.pop();
            completeMoves.push(tmp);
        }
        return completeMoves;
    }

    //Rook Moves
    private Stack getRookMoves(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Move validM, validM2, validM3, validM4;
        for(int i=1;i < 8;i++){
            int tmpx = x+i;
            int tmpy = y;
            if(!(tmpx > 7 || tmpx < 0)){
                Square tmp = new Square(tmpx, tmpy, piece);
                validM = new Move(startingSquare, tmp);
                if(!piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
                    moves.push(validM);
                }
                else{
                    if(checkWhiteOponent(((tmp.getXC()*75)+20), ((tmp.getYC()*75)+20))){
                        moves.push(validM);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int j=1;j < 8;j++){
            int tmpx1 = x-j;
            int tmpy1 = y;
            if(!(tmpx1 > 7 || tmpx1 < 0)){
                Square tmp2 = new Square(tmpx1, tmpy1, piece);
                validM2 = new Move(startingSquare, tmp2);
                if(!piecePresent(((tmp2.getXC()*75)+20), (((tmp2.getYC()*75)+20)))){
                    moves.push(validM2);
                }
                else{
                    if(checkWhiteOponent(((tmp2.getXC()*75)+20), ((tmp2.getYC()*75)+20))){
                        moves.push(validM2);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int k=1;k < 8;k++){
            int tmpx3 = x;
            int tmpy3 = y+k;
            if(!(tmpy3 > 7 || tmpy3 < 0)){
                Square tmp3 = new Square(tmpx3, tmpy3, piece);
                validM3 = new Move(startingSquare, tmp3);
                if(!piecePresent(((tmp3.getXC()*75)+20), (((tmp3.getYC()*75)+20)))){
                    moves.push(validM3);
                }
                else{
                    if(checkWhiteOponent(((tmp3.getXC()*75)+20), ((tmp3.getYC()*75)+20))){
                        moves.push(validM3);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int l=1;l < 8;l++){
            int tmpx4 = x;
            int tmpy4 = y-l;
            if(!(tmpy4 > 7 || tmpy4 < 0)){
                Square tmp4 = new Square(tmpx4, tmpy4, piece);
                validM4 = new Move(startingSquare, tmp4);
                if(!piecePresent(((tmp4.getXC()*75)+20), (((tmp4.getYC()*75)+20)))){
                    moves.push(validM4);
                }
                else{
                    if(checkWhiteOponent(((tmp4.getXC()*75)+20), ((tmp4.getYC()*75)+20))){
                        moves.push(validM4);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        return moves;
    }

    //Bishop Moves
    private Stack getBishopMoves(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Move validM, validM2, validM3, validM4;
        for(int i=1;i < 8;i++){
            int tmpx = x+i;
            int tmpy = y+i;
            if(!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)){
                Square tmp = new Square(tmpx, tmpy, piece);
                validM = new Move(startingSquare, tmp);
                if(!piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
                    moves.push(validM);
                }
                else{
                    if(checkWhiteOponent(((tmp.getXC()*75)+20), ((tmp.getYC()*75)+20))){
                        moves.push(validM);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        } // end of the first for Loop
        for(int k=1;k < 8;k++){
            int tmpk = x+k;
            int tmpy2 = y-k;
            if(!(tmpk > 7 || tmpk < 0 || tmpy2 > 7 || tmpy2 < 0)){
                Square tmpK1 = new Square(tmpk, tmpy2, piece);
                validM2 = new Move(startingSquare, tmpK1);
                if(!piecePresent(((tmpK1.getXC()*75)+20), (((tmpK1.getYC()*75)+20)))){
                    moves.push(validM2);
                }
                else{
                    if(checkWhiteOponent(((tmpK1.getXC()*75)+20), ((tmpK1.getYC()*75)+20))){
                        moves.push(validM2);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        } //end of second loop.
        for(int l=1;l < 8;l++){
            int tmpL2 = x-l;
            int tmpy3 = y+l;
            if(!(tmpL2 > 7 || tmpL2 < 0 || tmpy3 > 7 || tmpy3 < 0)){
                Square tmpLMov2 = new Square(tmpL2, tmpy3, piece);
                validM3 = new Move(startingSquare, tmpLMov2);
                if(!piecePresent(((tmpLMov2.getXC()*75)+20), (((tmpLMov2.getYC()*75)+20)))){
                    moves.push(validM3);
                }
                else{
                    if(checkWhiteOponent(((tmpLMov2.getXC()*75)+20), ((tmpLMov2.getYC()*75)+20))){
                        moves.push(validM3);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }// end of the third loop
        for(int n=1;n < 8;n++){
            int tmpN2 = x-n;
            int tmpy4 = y-n;
            if(!(tmpN2 > 7 || tmpN2 < 0 || tmpy4 > 7 || tmpy4 < 0)){
                Square tmpNmov2 = new Square(tmpN2, tmpy4, piece);
                validM4 = new Move(startingSquare, tmpNmov2);
                if(!piecePresent(((tmpNmov2.getXC()*75)+20), (((tmpNmov2.getYC()*75)+20)))){
                    moves.push(validM4);
                }
                else{
                    if(checkWhiteOponent(((tmpNmov2.getXC()*75)+20), ((tmpNmov2.getYC()*75)+20))){
                        moves.push(validM4);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }// end of the last loop
        return moves;
    }

    //Knight Moves
    private Stack getKnightMoves(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Stack attackingMove = new Stack();

        Square s = new Square(x+1, y+2, piece);
        moves.push(s);
        Square s1 = new Square(x+1, y-2, piece);
        moves.push(s1);
        Square s2 = new Square(x-1, y+2, piece);
        moves.push(s2);
        Square s3 = new Square(x-1, y-2, piece);
        moves.push(s3);
        Square s4 = new Square(x+2, y+1, piece);
        moves.push(s4);
        Square s5 = new Square(x+2, y-1, piece);
        moves.push(s5);
        Square s6 = new Square(x-2, y+1, piece);
        moves.push(s6);
        Square s7 = new Square(x-2, y-1, piece);
        moves.push(s7);

        for(int i=0;i < 8;i++){
            Square tmp = (Square)moves.pop();
            Move tmpmove = new Move(startingSquare, tmp);
            if((tmp.getXC() < 0)||(tmp.getXC() > 7)||(tmp.getYC() < 0)||(tmp.getYC() > 7)){

            }
            else if(piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
                if(piece.contains("White")){
                    if(checkWhiteOponent(((tmp.getXC()*75)+20), ((tmp.getYC()*75)+20))){
                        attackingMove.push(tmpmove);
                    }
                }
            }
            else{
                attackingMove.push(tmpmove);
            }
        }
        return attackingMove;
    }
    //check surrrounding squares
    private Boolean checkSurroundingSquares(Square s){
        Boolean possible = false;
        int x = s.getXC()*75;
        int y = s.getYC()*75;
        if(!((getPieceName((x+75), y).contains("BlackKing"))||(getPieceName((x-75), y).contains("BlackKing"))||(getPieceName(x,(y+75)).contains("BlackKing"))||(getPieceName((x), (y-75)).contains("BlackKing"))||(getPieceName((x+75),(y+75)).contains("BlackKing"))||(getPieceName((x-75),(y+75)).contains("BlackKing"))||(getPieceName((x+75),(y-75)).contains("BlackKing"))||(getPieceName((x-75), (y-75)).contains("BlackKing")))){
            possible = true;
        }
        return possible;
    }

    //Positions of all Pieces
    private Stack findWhitePieces() {
        Stack squares = new Stack();
        String icon;
        int x;
        int y;
        String pieceName;

        for (int i = 0; i < 600; i += 75) {
            for (int j = 0; j < 600; j += 75) {
                y = i / 75;
                x = j / 75;
                Component temp = chessBoard.findComponentAt(j, i);
                if (temp instanceof JLabel) {
                    chessPiece = (JLabel) temp;
                    icon = chessPiece.getIcon().toString();
                    pieceName = icon.substring(0, (icon.length() - 4));
                    if (pieceName.contains("White")) {
                        Square stmp = new Square(x, y, pieceName);
                        squares.push(stmp);
                    }
                }
            }
        }
        return squares;
    }
    private Stack findBlackPieces(){
        Stack squares = new Stack();
        String icon;
        int x;
        int y;
        String pieceName;

        for(int i=0;i < 600;i+=75){
            for(int j=0;j < 600;j+=75){
                y = i/75;
                x=j/75;
                Component tmp = chessBoard.findComponentAt(j, i);
                if(tmp instanceof JLabel){
                    chessPiece = (JLabel)tmp;
                    icon = chessPiece.getIcon().toString();
                    pieceName = icon.substring(0, (icon.length()-4));
                    if(pieceName.contains("Black")){
                        Square stmp = new Square(x, y, pieceName);
                        squares.push(stmp);
                    }
                }
            }
        }
        return squares;
    }
    //Landing Squares
    private void getLandingSquares(Stack found){
        Move tmp;
        Square landing;
        Stack squares = new Stack();
        while(!found.empty()){
            tmp = (Move)found.pop();
            landing = (Square)tmp.getLanding();
            squares.push(landing);
        }
        colorSquares(squares);
    }
    //Square Highlights
    private void colorSquares(Stack squares){
        Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 3);
        while(!squares.empty()){
            Square s = (Square)squares.pop();
            int location = s.getXC() + ((s.getYC())*8);
            JPanel panel = (JPanel)chessBoard.getComponent(location);
            panel.setBorder(greenBorder);
        }
    }
    //reset border for movement
    private void resetBorders(){
        Border empty = BorderFactory.createEmptyBorder();
        for(int i=0;i < 64;i++){
            JPanel tmppanel = (JPanel)chessBoard.getComponent(i);
            tmppanel.setBorder(empty);
        }
    }

    //FUCTIONS FOR ALL PIECE MOVEMENT STARTS HERE!!!
    //WhitePawn Code
    public boolean moveWPawn(){
        if(((landingX<0)||(landingX>7)||(landingY<0)||(landingY>7))){
            validMove=false;
        }
        else if(startY ==1){
            if((startX == landingX)&&(((startY - landingY)==-1)||(startY - landingY)==-2)){
                if(!piecePresent(landingX*75, landingY*75)){
                    validMove = true;
                }
                else{
                    validMove = false;
                }
            }
            else if((Math.abs(startX-landingX)==1)&&(((startY-landingY)==-1))){
                if (piecePresent(landingX*75,landingY*75)){
                    if(checkWhiteOponent(landingX*75,landingY*75)==true){
                        if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                            JOptionPane.showMessageDialog( null, "White Wins!" );
                        }
                        validMove = true;
                    }
                    else{
                        validMove =false;
                    }
                }
            }
            else{
                validMove = false;
            }
        }
        else if((Math.abs(startX-landingX)==1)&&(((startY-landingY)==-1))){
            if (piecePresent(landingX*75,landingY*75)){
                if(checkWhiteOponent(landingX*75,landingY*75)==true){
                    if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                        JOptionPane.showMessageDialog( null, "White Wins!" );
                    }
                    validMove = true;
                    if(landingY==7){
                        success = true;
                    }
                }
                else{
                    validMove =false;
                }
            }
            else{
                validMove = false;
            }
        }
        else{
            if((startX == landingX)&&(((startY - landingY)==-1))){
                if(!piecePresent(landingX*75, landingY*75)){
                    validMove = true;
                    if(landingY==7){
                        success = true;
                    }
                }
                else{
                    validMove = false;

                }
            }
            else{
                validMove = false;
            }
        }
        return validMove;
    }
    //BlackPawn Code
    public boolean moveBPawn(){
        if(((landingX<0)||(landingX>7)||(landingY<0)||(landingY>7))){
            validMove=false;
        }
        else if(startY ==6){
            if((startX == landingX)&&(((startY - landingY)==1)||(startY - landingY)==2)){
                if(!piecePresent(landingX*75, landingY*75)){
                    validMove = true;

                }else{
                    validMove = false;
                }
            }
            else if((Math.abs(startX-landingX)==1)&&(((startY-landingY)==1))){
                if (piecePresent(landingX*75,landingY*75)){
                    if(checkBlackOponent(landingX*75,landingY*75)==true){
                        if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                            JOptionPane.showMessageDialog( null, "Black Wins!" );
                        }
                        validMove = true;
                    }
                    else{
                        validMove =false;
                    }
                }
            }
            else{
                validMove = false;
            }
        }
        else {
            if ((Math.abs(startX - landingX) == 1) && (((startY - landingY) == 1))) {
                if (piecePresent(landingX * 75, landingY * 75)) {
                    if (checkBlackOponent(landingX * 75, landingY * 75)) {
                        if (piecePresent(landingX * 75, landingY * 75) && WhiteKing(landingX * 75, landingY * 75)) {
                            JOptionPane.showMessageDialog(null, "Black Wins!");
                        }
                        validMove = true;
                        if (landingY == 0) {
                            progression = true;
                        }
                    } else {
                        validMove = false;
                    }
                } else {
                    validMove = false;
                }
            } else {
                if ((startX == landingX) && (((startY - landingY) == 1))) {
                    if (!piecePresent(landingX * 75, landingY * 75)) {
                        validMove = true;
                        if (landingY == 0) {
                            progression = true;
                        }
                    } else {
                        validMove = false;
                    }
                } else {
                    validMove = false;
                }
            }
        }
        return validMove;
    }
    //Queen Code
    public boolean moveQueen(){
        if(((landingX<0)||(landingX>7)||(landingY<0)||(landingY>7))){
            validMove=false;
        }
        else if(Math.abs(startX-landingX)!=0 && Math.abs(startY-landingY)==0||
                Math.abs(startX-landingX)==0 && Math.abs(startY-landingY)!=0||
                Math.abs(startX-landingX)==Math.abs(startY-landingY)){
            if ((startX-landingX)<0&&(startY-landingY)<0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX+i)*75),((startY+i)*75))){
                        InTheWay = true;
                    }
                }
            }
            else if ((startX-landingX)<0&&(startY-landingY)>0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX+i)*75),((startY-i)*75))){
                        InTheWay = true;
                    }
                }
            }
            else if ((startX-landingX)>0&&(startY-landingY)>0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX-i)*75),((startY-i)*75))){
                        InTheWay = true;
                    }
                }
            }
            else if ((startX-landingX)>0&&(startY-landingY)<0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX-i)*75),((startY+i)*75))){
                        InTheWay = true;
                    }
                }
            }
            else if(Math.abs(startX-landingX)!=0){
                if(startX-landingX>0){
                    for (int i =0;i<Math.abs(landingX-startX);i++){
                        if(piecePresent((startX-i)*75,(startY)*75)){
                            InTheWay = true;
                        }
                    }
                }
                else if(startX-landingX<0){
                    for (int i =0;i<Math.abs(landingX-startX);i++){
                        if(piecePresent((startX+i)*75,(startY)*75)){
                            InTheWay = true;
                        }
                    }
                }
            }
            else if (Math.abs(startY-landingY)!=0){
                if(startY-landingY>0){
                    for (int i =0;i<Math.abs(landingY-startY);i++){
                        if(piecePresent((startX)*75,(startY-i)*75)){
                            InTheWay = true;
                        }
                    }
                }
                else if(startY-landingY<0){
                    for (int i =0;i<Math.abs(landingY-startY);i++){
                        if(piecePresent((startX)*75,(startY+i)*75)){
                            InTheWay = true;
                        }
                    }
                }
            }
            if(InTheWay==true){
                validMove=false;
            }
            else if(InTheWay==false){
                if(piecePresent(landingX*75,landingY*75)){
                    if(pieceName.contains("White")){
                        if(checkWhiteOponent(landingX*75,landingY*75)){
                            if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "White Wins!" );
                            }
                            validMove=true;
                        }
                        else{
                            validMove=false;
                        }
                    }
                    else if(pieceName.contains("Black")){
                        if(checkBlackOponent(landingX*75,landingY*75)){
                            if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "Black Wins!" );
                            }
                            validMove=true;
                        }
                        else{
                            validMove=false;
                        }
                    }
                }
                else{
                    validMove=true;
                }
            }
        }
        else{
            validMove=false;
        }
        return validMove;
    }
    //King Code
    public boolean moveKing(){
        if(((landingX<0)||(landingX>7)||(landingY<0)||(landingY>7))){
            validMove=false;
        }
        else if(Math.abs(startX - landingX) <= 1 && Math.abs(startY - landingY) <= 1) {
            //White King
            if (pieceName.contains("White")) {
                if ((landingX==0&&landingY==0)||(landingX==0&&landingY==7)||(landingX==7&&landingY==0)||(landingX==7&&landingY==7)){
                    if (piecePresent(landingX*75, landingY*75)){
                        if(checkWhiteOponent(landingX*75, landingY*75)){
                            validMove=true;
                        }else{
                            validMove=false;
                        }
                    }
                    else{
                        validMove=true;
                    }
                }
                else if(landingX > 0&&landingX <7 && landingY >0&& landingY<7) {
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 + 75) && kingCheckKing(landingX*75 + 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 - 75) && kingCheckKing(landingX*75 + 75, landingY*75 - 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 - 75) && kingCheckKing(landingX*75 - 75, landingY*75 - 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 + 75) && kingCheckKing(landingX*75 - 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkWhiteOponent(landingX*75, landingY*75)) {
                            if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "White Wins!" );
                            }
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if(landingY==0&&landingX!=0||landingY==0&&landingX!=7){
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 + 75, landingY*75 + 75) && kingCheckKing(landingX*75 + 75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75 + 75) && kingCheckKing(landingX*75 - 75, landingY*75 + 75)) {
                        validMove = false;
                    }
                    else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkWhiteOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if(landingY==7&&landingX!=0||landingY==7&&landingX!=7){
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75 - 75) && kingCheckKing(landingX*75 - 75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 + 75, landingY*75 - 75) && kingCheckKing(landingX*75 + 75, landingY*75 - 75)) {
                        validMove = false;
                    }
                    else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkWhiteOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if (landingX==0&&landingY!=0&&landingX==0&&landingY!=7){
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 + 75) && kingCheckKing(landingX*75 + 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 - 75) && kingCheckKing(landingX*75 + 75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkWhiteOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if (landingX==7&&landingY!=0&&landingX==7&&landingY!=7){
                    if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 + 75) && kingCheckKing(landingX*75 - 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 - 75) && kingCheckKing(landingX*75 - 75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkWhiteOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                /*//issue with corners
                else if ((landingX==0&&landingY==0)||(landingX==0&&landingY==7)||(landingX==7&&landingY==0)||(landingX==7&&landingY==7)){
                    if (piecePresent(landingX*75, landingY*75)){
                        if(checkWhiteOponent(landingX*75, landingY*75)){
                            validMove=true;
                        }else{
                            validMove=false;
                        }
                    }
                    else{
                        validMove=true;
                    }
                }*/
            }
            else if (pieceName.contains("Black")) {
                if (landingX==0&&landingY==0||landingX==0&&landingY==7||landingX==7&&landingY==0||landingX==7&&landingY==7){
                    if (piecePresent(landingX*75, landingY*75)){
                        if(checkBlackOponent(landingX*75, landingY*75)){
                            validMove=true;
                        }else{
                            validMove=false;
                        }
                    }
                    else{
                        validMove=true;
                    }
                }
                else if(landingX > 0&&landingX <7 && landingY >0&& landingY<7) {
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 + 75) && kingCheckKing(landingX*75 + 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 - 75) && kingCheckKing(landingX*75 + 75, landingY*75 - 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 - 75) && kingCheckKing(landingX*75 - 75, landingY*75 - 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 + 75) && kingCheckKing(landingX*75 - 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkBlackOponent(landingX*75, landingY*75)) {
                            if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "Black Wins!" );
                            }
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if(landingY==0&&landingX!=0||landingY==0&&landingX!=7){
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 + 75, landingY*75 + 75) && kingCheckKing(landingX*75 + 75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75 + 75) && kingCheckKing(landingX*75 - 75, landingY*75 + 75)) {
                        validMove = false;
                    }
                    else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkWhiteOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if(landingY==7&&landingX!=0||landingY==7&&landingX!=7){
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 - 75, landingY*75 - 75) && kingCheckKing(landingX*75 - 75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75 + 75, landingY*75 - 75) && kingCheckKing(landingX*75 + 75, landingY*75 - 75)) {
                        validMove = false;
                    }
                    else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkBlackOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if (landingX==0&&landingY!=0&&landingX==0&&landingY!=7){
                    if (piecePresent(landingX*75 + 75, landingY*75) && kingCheckKing(landingX*75 + 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 + 75) && kingCheckKing(landingX*75 + 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 + 75, landingY*75 - 75) && kingCheckKing(landingX*75 + 75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkBlackOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
                else if (landingX==7&&landingY!=0&&landingX==7&&landingY!=7){
                    if (piecePresent(landingX*75 - 75, landingY*75) && kingCheckKing(landingX*75 - 75, landingY*75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 + 75) && kingCheckKing(landingX*75 - 75, landingY*75 + 75)) {
                        validMove = false;
                    } else if (piecePresent(landingX*75 - 75, landingY*75 - 75) && kingCheckKing(landingX*75 - 75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 + 75) && kingCheckKing(landingX*75, landingY*75 + 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75 - 75) && kingCheckKing(landingX*75, landingY*75 - 75)) {
                        validMove = false;
                    }else if (piecePresent(landingX*75, landingY*75)) {
                        if (checkBlackOponent(landingX*75, landingY*75)) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = true;
                    }
                }
            }
        }
        else{
            validMove = false;
        }
        return validMove;
    }
    //Rook Code
    public boolean moveRook(){
        if(((landingX<0)||(landingX>7)||(landingY<0)||(landingY>7))){
            validMove=false;
        }
        else if(Math.abs(startX-landingX)!=0 && Math.abs(startY-landingY)==0||
                Math.abs(startX-landingX)==0 && Math.abs(startY-landingY)!=0){
            if(Math.abs(startX-landingX)!=0){
                if(startX-landingX>0){
                    for (int i =0;i<Math.abs(landingX-startX);i++){
                        if(piecePresent((startX-i)*75,(startY)*75)){
                            InTheWay = true;
                        }
                    }
                }
                else if(startX-landingX<0){
                    for (int i =0;i<Math.abs(landingX-startX);i++){
                        if(piecePresent((startX+i)*75,(startY)*75)){
                            InTheWay = true;
                        }
                    }
                }
            }
            else if (Math.abs(startY-landingY)!=0){
                if(startY-landingY>0){
                    for (int i =0;i<Math.abs(landingY-startY);i++){
                        if(piecePresent((startX)*75,(startY-i)*75)){
                            InTheWay = true;
                        }
                    }
                }
                else if(startY-landingY<0){
                    for (int i =0;i<Math.abs(landingY-startY);i++){
                        if(piecePresent((startX)*75,(startY+i)*75)){
                            InTheWay = true;
                        }
                    }
                }
            }
            if(InTheWay==true){
                validMove = false;
            }
            else if (InTheWay==false){
                if(piecePresent(landingX*75,landingY*75)){
                    if (pieceName.contains("White")){
                        if(checkWhiteOponent(landingX*75,landingY*75)){
                            if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "White Wins!" );
                            }
                            validMove = true;
                        }
                        else{
                            validMove = false;
                        }
                    }
                    else if (pieceName.contains("Black")){
                        if(checkBlackOponent(landingX*75,landingY*75)){
                            if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "Black Wins!" );
                            }
                            validMove = true;
                        }
                        else{
                            validMove = false;
                        }
                    }
                }
                else{
                    validMove = true;
                }
            }
        }
        else{
            validMove = false;
        }
        return validMove;
    }
    //Kight Code
    public boolean moveKnight(){
        if(((landingX<0)||(landingX>7)||(landingY<0)||(landingY>7))){
            validMove=false;
        }
        else if(startX +2==landingX||startX -2==landingX){
            if(startY +1==landingY||startY -1==landingY){
                if(!piecePresent(landingX*75,landingY*75)){
                    validMove= true;
                }
                else if(piecePresent(landingX*75,landingY*75)){
                    if(pieceName.contains("White")){
                        if(checkWhiteOponent(landingX*75,landingY*75)==true){
                            if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "White Wins!" );
                            }
                            validMove = true;
                        }
                        else{
                            validMove = false;
                        }
                    }
                    if(pieceName.contains("Black")){
                        if(checkBlackOponent(landingX*75,landingY*75)==true){
                            if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "Black Wins!" );
                            }
                            validMove = true;
                        }else{
                            validMove = false;
                        }
                    }
                }
            }
        }
        else if(startY +2==landingY||startY -2==landingY){
            if(startX +1==landingX||startX -1==landingX){
                if(!piecePresent(landingX*75,landingY*75)){
                    validMove= true;
                }
                else if(piecePresent(landingX*75,landingY*75)){
                    if(pieceName.contains("White")){
                        if(checkWhiteOponent(landingX*75,landingY*75)==true){
                            if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "White Wins!" );
                            }
                            validMove = true;
                        }else{
                            validMove = false;
                        }
                    }
                    if(pieceName.contains("Black")){
                        if(checkBlackOponent(landingX*75,landingY*75)==true){
                            validMove = true;
                        }else{
                            if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "Black Wins!" );
                            }
                            validMove = false;
                        }
                    }
                }
            }
        }
        else{
            validMove= false;
        }
        return validMove;
    }
    //Bishop Code
    public boolean moveBishop(){
        if(((landingX<0)||(landingX>7)||(landingY<0)||(landingY>7))){
            validMove=false;
        }
        else if(xMovement==yMovement){
            if ((startX-landingX)<0&&(startY-landingY)<0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX+i)*75),((startY+i)*75))){
                        InTheWay = true;
                    }
                }
            }
            else if ((startX-landingX)<0&&(startY-landingY)>0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX+i)*75),((startY-i)*75))){
                        InTheWay = true;
                    }
                }
            }
            else if ((startX-landingX)>0&&(startY-landingY)>0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX-i)*75),((startY-i)*75))){
                        InTheWay = true;
                    }
                }
            }
            else if ((startX-landingX)>0&&(startY-landingY)<0){
                for(int i= 0; i<xMovement;i++){
                    if(piecePresent(((startX-i)*75),((startY+i)*75))){
                        InTheWay = true;
                    }
                }
            }

            if (InTheWay==true){
                validMove=false;
            }
            else{
                if(piecePresent(landingX*75,landingY*75)){
                    if(pieceName.contains("White")){
                        if(checkWhiteOponent(landingX*75,landingY*75)){
                            if(piecePresent(landingX*75,landingY*75)&&BlackKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "White Wins!" );
                            }
                            validMove=true;
                        }
                        else{
                            validMove=false;
                        }
                    }
                    else if(pieceName.contains("Black")){
                        if(checkBlackOponent(landingX*75,landingY*75)){
                            if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                                JOptionPane.showMessageDialog( null, "Black Wins!" );
                            }
                            validMove=true;
                        }
                        else{
                            validMove=false;
                        }
                    }
                }
                else{
                    validMove=true;
                }
            }
        }
        else{
            validMove=false;
        }
        return validMove;
    }

    public void mouseClicked(MouseEvent e) {

    }
    public void mouseMoved(MouseEvent e) {
   }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e) {

    }

	/*
		Main method that gets the ball moving.
	*/
    public static void main(String[] args) {
        JFrame frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);

        Object[] options = {"Two Player","Random Moves"};
        int n = JOptionPane.showOptionDialog(frame,"Lets play some Chess, choose your game mode","Introduction to AI Continuous Assessment", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
        System.out.println("The selected variable is : "+n);
        mode = n;
        JOptionPane.showMessageDialog( null, "Black Turn First" );
     }
  }

