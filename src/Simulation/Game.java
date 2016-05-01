package Simulation;

public class Game {

    private Status theStatus;
    private Lane onThisLane;

    public Game(Lane lane) {
        onThisLane = lane;
    }

    public boolean hasNextTurn() {
        // TODO
    }

    public void nextTurn() {

    }


    /*
    if (partyAssigned && !gameFinished) {	// we have a party on this lane,
        // so next bower can take a throw

        while (gameIsHalted) {
            try {
                sleep(10);
            } catch (Exception e) {}
        }


        if (bowlerIterator.hasNext()) {
            currentThrower = (Bowler)bowlerIterator.next();

            canThrowAgain = true;
            tenthFrameStrike = false;
            ball = 0;
            while (canThrowAgain) {
                setter.ballThrown();		// simulate the thrower's ball hiting
                ball++;
            }

            if (frameNumber == 9){
                finalScores[bowlIndex][gameNumber] = cumulScores[bowlIndex][9];
                try{
                    Date date = new Date();
                    String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                    ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, new Integer(cumulScores[bowlIndex][9]).toString());
                } catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
            }


            setter.reset();
            bowlIndex++;

        } else {
            frameNumber++;
            resetBowlerIterator();
            bowlIndex = 0;
            if (frameNumber > 9) {
                gameFinished = true;
                gameNumber++;
            }
        }
    }*/


}
