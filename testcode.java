else if (pieceName.contains("Rook")){
	if(startX == landingX){
		if(landingY != startY){
			validMove = true;
		}
		else{
			validMove= false;
		}
	}
	else if (startY == landingY){
		if(landingX != startX){
			for (int i=1; i<=(landingX-startX); i++){
				if(checkWhiteOponent((startX+i),(startY+i))==true){
					if ((startX+i)==landingX&&(startY+i)==landingY){
						validMove = true;
					}else{
						validMove = false;
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
		}
		}





  int landingX = (e.getX()/75);
  int landingY = (e.getY()/75);
  int xMovement = Math.abs((e.getX()/75)-startX);
  int yMovement = Math.abs((e.getY()/75)-startY);

  System.out.println("-------------------------------------------------");
  System.out.println("This piece:"+pieceName);
  System.out.println("Starting position:"+"("+startX+","+startY+")");
  System.out.println("xMovement is:"+xMovement);
  System.out.println("yMovement is:"+yMovement);
  System.out.println("Landing position"+"("+landingX+","+landingY+")");
  System.out.println("-------------------------------------------------");

  else if((Math.abs(startX-landingX)==1)&&(((startY-landingY)==1))){
    if (piecePresent(e.getX(),e.getY())){
      if(checkBlackOponent(e.getX(),e.getY())){
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

  if(pieceName.equals("WhitePawn")){
        if(startY == 1){
          if((startY+1==landingY||startY+2==landingY)&&startX==landingX){
            if(!piecePresent(e.getX(),e.getY())){
              validMove = true;
            }
            else{
              validMove = false;
            }
          }
          else{
            validMove = false;
          }
        }
        else if(startY != 1){
          if(startY+1==landingY&&startX==landingX){
            if(!piecePresent(e.getX(),e.getY())){
              validMove = true;
            }
            else{
              validMove= false;
            }
          }
          else{
            validMove= false;
          }
        }
        else if(startY+1==landingY&&(startX-1==landingX||startX+1==landingX)){
          if(piecePresent(e.getX(),e.getY())){
            if(checkWhiteOponent(e.getX(), e.getY())){
              validMove = true;
            }
            else{
              validMove = false;
            }
          }
          else{
            validMove = false;
          }
        }
  	}
