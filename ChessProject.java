import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JOptionPane;
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
    private int turnNum =1;
    private Boolean whiteTurn;


	Boolean validMove = false;
	JPanel panels;
	JLabel pieces;

	int moves = 0;

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

        JOptionPane.showMessageDialog( null, "White Turn First" );
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
                    turnNum++;
                    break;
                case "WhiteKnight":
                    moveKnight();
                    turnNum++;
                    break;
                case "WhiteBishop":
                    moveBishop();
                    turnNum++;
                    break;
                case "WhiteRook":
                    moveRook();
                    turnNum++;
                    break;
                case "WhiteQueen":
                    moveQueen();
                    turnNum++;
                    break;
                case "WhiteKing":
                    moveKing();
                    turnNum++;
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
                    turnNum++;
                    break;
                case "BlackKnight":
                    moveKnight();
                    turnNum++;
                    break;
                case "BlackBishop":
                    moveBishop();
                    turnNum++;
                    break;
                case "BlackRook":
                    moveRook();
                    turnNum++;
                    break;
                case "BlackQueen":
                    moveQueen();
                    turnNum++;
                    break;
                case "BlackKing":
                    moveKing();
                    turnNum++;
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


    }

    //WhitePawn Code
    public boolean moveWPawn(){
        if(startY ==1){
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
        if(startY ==6){
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
        else if((Math.abs(startX-landingX)==1)&&(((startY-landingY)==1))){
            if (piecePresent(landingX*75,landingY*75)){
                if(checkBlackOponent(landingX*75,landingY*75)){
                    if(piecePresent(landingX*75,landingY*75)&&WhiteKing(landingX*75,landingY*75)){
                        JOptionPane.showMessageDialog( null, "Black Wins!" );
                    }
                    validMove = true;
                    if(landingY==0){
                        progression = true;
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
            if((startX == landingX)&&(((startY - landingY)==1))){
                if(!piecePresent(landingX*75, landingY*75)){
                    validMove = true;
                    if(landingY==0){
                        progression = true;
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
    //Queen Code
    public boolean moveQueen(){
        if(Math.abs(startX-landingX)!=0 && Math.abs(startY-landingY)==0||
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
                //issue with corners
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
                }
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
        if(Math.abs(startX-landingX)!=0 && Math.abs(startY-landingY)==0||
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
        if(startX +2==landingX||startX -2==landingX){
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
        if((Math.abs(startX-landingX))==(Math.abs(startY-landingY))){
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
            else if (InTheWay==false){
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
            validMove=true;
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
     }

    /*//setMethods
    public void setlandingX(int landingX){
        this.landingX = landingX;
    }
     //get methods
    public boolean getValidmove(){
        return validMove;
    }*/
  }

