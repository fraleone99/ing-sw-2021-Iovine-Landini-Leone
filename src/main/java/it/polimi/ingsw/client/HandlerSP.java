package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.View;
//import it.polimi.ingsw.controller.LocalSPController;
import it.polimi.ingsw.controller.LocalSPController;
import it.polimi.ingsw.server.answer.Answer;
import it.polimi.ingsw.server.answer.finalanswer.Lose;
import it.polimi.ingsw.server.answer.finalanswer.Win;
import it.polimi.ingsw.server.answer.infoanswer.ActionTokenInfo;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.server.answer.initialanswer.Connection;
import it.polimi.ingsw.server.answer.request.RequestDoubleInt;
import it.polimi.ingsw.server.answer.request.RequestInt;
import it.polimi.ingsw.server.answer.request.RequestString;
import it.polimi.ingsw.server.answer.request.SendMessage;
import it.polimi.ingsw.server.answer.seegameboard.*;
import it.polimi.ingsw.server.answer.turnanswer.*;

public class HandlerSP implements Runnable, Handler {
    private final View view;
    final Object lock = new Object();
    String answer;
    int number;
    int number2;
    boolean isReady=false;

    public HandlerSP(View view) {
        this.view = view;
    }

    public void send(Object message) {
        synchronized (lock) {
            if(message instanceof SendString) {
                answer=((SendString) message).getString();
            } else if(message instanceof SendInt) {
                number=(((SendInt) message).getChoice());
            } else if(message instanceof SendDoubleInt) {
                number=(((SendDoubleInt) message).getChoice1());
                number2=(((SendDoubleInt) message).getChoice2());
            }
            isReady=true;
            lock.notifyAll();
        }
    }


    @Override
    public void run() {
        view.askNickname("Insert nickname: ");

        synchronized (lock) {
            while(!isReady) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        setReady(false);

        LocalSPController controller= new LocalSPController(answer,this);
        controller.localGame();
    }

    public Object getLock() {
        return lock;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getNumber() {
        return number;
    }

    public int getNumber2() {
        return number2;
    }

    public String getAnswer() {
        return answer;
    }

    public void handleClient(Answer inputObj) {
        if (inputObj instanceof Connection) {
            if (((Connection) inputObj).isConnection()) {
                view.handShake(((Connection) inputObj).getMessage());
            }
        } else if (inputObj instanceof RequestString) {
            view.askNickname(((RequestString) inputObj).getMessage());
        } else if (inputObj instanceof RequestInt) {
            switch (((RequestInt) inputObj).getType()) {
                case "NUMBER":
                    view.askPlayerNumber(((RequestInt) inputObj).getMessage());
                    break;
                case "RESOURCE":
                    view.askResource(((RequestInt) inputObj).getMessage());
                    break;
                case "GAMEBOARD":
                    view.seeGameBoard(((RequestInt) inputObj).getMessage());
                    break;
                case "LINE":
                    view.chooseLine(((RequestInt) inputObj).getMessage());
                    break;
                case "TURN":
                    view.askTurnType(((RequestInt) inputObj).getMessage());
                    break;
                case "STORAGE":
                    view.ManageStorage(((RequestInt) inputObj).getMessage());
                    break;
                case "MARKET":
                    view.useMarket(((RequestInt) inputObj).getMessage());
                    break;
                case "WHITE":
                    view.chooseWhiteBallLeader(((RequestInt) inputObj).getMessage());
                    break;
                case "SPACE":
                    view.askSpace(((RequestInt) inputObj).getMessage());
                    break;
                case "TYPE":
                    view.askType(((RequestInt) inputObj).getMessage());
                    break;
                case "INPUT":
                    view.askInput(((RequestInt) inputObj).getMessage());
                    break;
                case "OUTPUT":
                    view.askOutput(((RequestInt) inputObj).getMessage());
                    break;
                case "DEVCARD":
                    view.askDevelopmentCard(((RequestInt) inputObj).getMessage());
                    break;
                case "LEADCARD":
                    view.askLeaderCard(((RequestInt) inputObj).getMessage());
                    break;
                case "CHOICE":
                    view.seeMoreFromTheGameBoard();
                    break;
                case "SHELF":
                    view.chooseShelf();
                    break;
                case "END":
                    view.endTurn(((RequestInt) inputObj).getMessage());
                    break;
            }
        } else if (inputObj instanceof SendMessage) {
            view.readMessage(((SendMessage) inputObj).getMessage());
        } else if (inputObj instanceof InitializeGameBoard) {
            view.initializeGameBoard(((InitializeGameBoard) inputObj).getMarket(), ((InitializeGameBoard) inputObj).getIdDevCards(), ((InitializeGameBoard) inputObj).getLeaderCards());
        } else if (inputObj instanceof Win) {
            view.win(((Win) inputObj).getMessage());
        } else if (inputObj instanceof Lose) {
            view.lose(((Lose) inputObj).getMessage());
        } else if (inputObj instanceof FaithPathInfo) {
            view.printFaithPath((FaithPathInfo) inputObj);
        } else if (inputObj instanceof SeeOtherCards) {
            view.seeOtherCards(((SeeOtherCards) inputObj).getMessage());
        } else if (inputObj instanceof StorageInfo) {
            if(((StorageInfo) inputObj).getCoinsAmount()==-1) {
                view.printStorage((StorageInfo)inputObj);
            } else {
                view.printStorageAndVault((StorageInfo) inputObj);
            }
        } else if (inputObj instanceof DevCardsSpaceInfo) {
            view.printDevelopmentCardsSpace((DevCardsSpaceInfo) inputObj);
        } else if (inputObj instanceof ResetCard) {
            view.resetCard(((ResetCard) inputObj).getPos());
        } else if (inputObj instanceof RequestDoubleInt) {
            if(((RequestDoubleInt) inputObj).getType().equals("DEVCARD")) {
                view.askCardToBuy(((RequestDoubleInt) inputObj).getCards(), ((RequestDoubleInt) inputObj).getSpaces());
            } else {
                view.MoveShelves(((RequestDoubleInt) inputObj).getMessage());
            }        } else if (inputObj instanceof SeeBall) {
            view.seeBall((SeeBall) inputObj);
        } else if (inputObj instanceof ActionTokenInfo) {
            view.printActionToken(((ActionTokenInfo) inputObj).getMessage());
        } else {
            if (inputObj instanceof PassLeaderCard) {
                view.askLeaderToDiscard(((PassLeaderCard) inputObj).getMessage());
            } else if (inputObj instanceof SeeLeaderCards) {
                view.seeLeaderCards(((SeeLeaderCards) inputObj).getMessage());
            } else if (inputObj instanceof SeeMarket) {
                view.seeMarket(((SeeMarket) inputObj).getMessage());
            } else if (inputObj instanceof ActiveLeader) {
                view.activeLeader(((ActiveLeader) inputObj));
            } else if (inputObj instanceof DiscardLeader) {
                view.discardLeader(((DiscardLeader) inputObj));
            } else if (inputObj instanceof SeeGrid) {
                view.seeGrid(((SeeGrid) inputObj).getMessage());
            } else if (inputObj instanceof SeeProductions) {
                view.seeProductions(((SeeProductions) inputObj).getMessage());
            }
        }
    }
}