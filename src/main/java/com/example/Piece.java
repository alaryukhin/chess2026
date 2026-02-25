package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
         
        try {
            if (this.img == null) {
                this.img = ImageIO.read(new File(System.getProperty("user.dir")+img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    

    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    //precondition: g and currentSquare must be on-null valid objects.
    //postcondition: the image stored in the img property of this object is drawn to the screen.
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
     ArrayList<Square> controlled = new ArrayList<>();
     int r = start.getRow();
     int c = start.getCol();
     // pawns capture diagonally one forward in either direction
     int dir = this.color ? -1 : 1; // white moves up (decreasing row), black moves down

     int dr = r + dir;
     if (dr >= 0 && dr < 8) {
         if (c - 1 >= 0) controlled.add(board[dr][c - 1]);
         if (c + 1 < 8) controlled.add(board[dr][c + 1]);
     }

     return controlled;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> moves = new ArrayList<>();
    	Square[][] board = b.getSquareArray();
    	int r = start.getRow();
    	int c = start.getCol();
    	int dir = this.color ? -1 : 1; // white moves up, black moves down

    	// forward two squares (this variant moves 2 instead of 1)
    	int f1 = r + dir;
    	int f2 = r + 2 * dir;
    	if (f2 >= 0 && f2 < 8) {
    		// ensure both intermediate and target squares are empty
    		if (!board[f1][c].isOccupied() && !board[f2][c].isOccupied()) {
    			moves.add(board[f2][c]);
    		}
    	}

    	// captures: diagonally one forward
    	if (f1 >= 0 && f1 < 8) {
    		if (c - 1 >= 0 && board[f1][c - 1].isOccupied()
    			&& board[f1][c - 1].getOccupyingPiece().getColor() != this.color) {
    			moves.add(board[f1][c - 1]);
    		}
    		if (c + 1 < 8 && board[f1][c + 1].isOccupied()
    			&& board[f1][c + 1].getOccupyingPiece().getColor() != this.color) {
    			moves.add(board[f1][c + 1]);
    		}
    	}

    	return moves;
    }
}