package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.gameboard.playerdashboard.*;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.observer.VirtualViewObservable;
import it.polimi.ingsw.server.answer.finalanswer.Lose;
import it.polimi.ingsw.server.answer.finalanswer.Win;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.initialanswer.Connection;
import it.polimi.ingsw.server.answer.request.RequestDoubleInt;
import it.polimi.ingsw.server.answer.request.RequestInt;
import it.polimi.ingsw.server.answer.request.RequestString;
import it.polimi.ingsw.server.answer.request.SendMessage;
import it.polimi.ingsw.server.answer.seegameboard.*;
import it.polimi.ingsw.server.answer.turnanswer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Virtual view class manages the communication between the server and the client
 *
 * @author Lorenzo Iovine
 */
public class VirtualView extends VirtualViewObservable {
    private int number;
    private final Map <String, ClientHandler> namesToClient = new HashMap<>();
    private final Map <String, Boolean> clientConnected = new HashMap<>();
    private Controller controller;
    private final Server server;

    public VirtualView(Server server) {
        this.server = server;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Sets the value of the Hash maps of the virtual view and the controller (RESILIENCE)
     * @param nickname the nickname to sets in the map
     * @param client the client handler related of the nickname
     * @param crashed if he is crashed
     */
    public void setNamesToClient(String nickname, ClientHandler client, boolean crashed) {
        if(crashed) {
            clientConnected.replace(nickname, true);
            namesToClient.replace(nickname, client);
            controller.setClientConnection(nickname, true, true);
        } else {
            namesToClient.put(nickname, client);
            clientConnected.put(nickname, true);
        }
    }

    /**
     * Sets the initial maps of the controller (RESILIENCE)
     */
    public void setClientConnectionToController() {
        for(String nickname : clientConnected.keySet()) {
            controller.setClientConnection(nickname, true, false);
        }
    }

    /**
     * Updates the maps when a player crashes (RESILIENCE)
     * @param nickname the nickname of the client crashed
     */
    public void clientCrashed(String nickname) {
        clientConnected.replace(nickname, false);
        controller.setClientConnection(nickname, false, true);
    }

    /**
     * Waits for the client to send an int
     * @param client is the clientHandler of the client form which the server is waiting an int
     * @return the int received from the client
     */
    public int waitForInt(ClientHandler client) {
        synchronized (client.getLock()) {
            while(!client.isReady()) {
                try {
                    client.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            number = client.getNumber();
        }
        client.setReady(false);
        return number;
    }

    /**
     * Waits for the client to send two int
     * @param client is the clientHandler of the client form which the server is waiting two int
     * @return an arrayList containing the two int
     */
    public ArrayList<Integer> waitForDoubleInt(ClientHandler client) {
        ArrayList<Integer> numbers = new ArrayList<>();

        synchronized (client.getLock()) {
            while (!client.isReady()) {
                try {
                    client.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            numbers.add(client.getNumber());
            numbers.add(client.getNumber2());
        }
        client.setReady(false);
        return numbers;
    }

    /**
     * Waits for the client to send a string
     * @param client is the clientHandler of the client form which the server is waiting the string
     * @return the string received from the client
     */
    public String waitForString(ClientHandler client) {
        String answer;

        synchronized (client.getLock()) {
            while(!client.isReady()) {
                try {
                    client.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            answer = client.getAnswer();
        }

        client.setReady(false);
        return answer;
    }

    /**
     * Is the first message sent when a client connects to the server
     * @param client the clientHandler of the recipient of the message
     */
    public void askHandShake(ClientHandler client) {
        client.send(new Connection("Welcome to this fantastic server!", true));
    }

    /**
     * Asks the nickname to the client
     * @param client is the clientHandler of the client to which the message is sent
     * @return the nickname chosen by the client
     */
    public String requestNickname(ClientHandler client){
        client.send(new RequestString("Please insert your nickname:"));
        return waitForString(client);
    }

    /**
     * Informs the client that the chosen nickname is invalid
     * @param client is the clientHandler of the client to which the message is sent
     * @return the new nickname chosen by the client
     */
    public String invalidNickname(ClientHandler client){
        client.send(new RequestString("The chosen nickname is not valid. Try again:"));
        return waitForString(client);
    }

    /**
     * Asks the first player of the lobby the numbers of player
     * @param client is the clientHandler of the client to which the message is sent
     * @return the number of player
     */
    public int requestPlayersNumber(ClientHandler client){
        client.send(new RequestInt("NUMBER","Please insert the number of players:"));
        return waitForInt(client);
        //notify everyone the players number
    }

    /**
     *Sends a message to the client informing him that he is in the waiting room
     * @param client is the clientHandler of the client to which the message is sent
     */
    public void waitingRoom(ClientHandler client){
        client.send(new SendMessage("You are now in the waiting room. The game will start soon!"));
    }

    /**
     * Notifies the preparation of the lobby
     */
    public void prepareTheLobby(){
        notifyPreparationOfLobby();
    }

    /**
     * Informs the player that he is the first to play
     * @param nickname recipient of the message
     */
    public void firstPlayer(String nickname) {
        namesToClient.get(nickname).send(new SendMessage("You are the first player."));
    }

    /**
     * Asks the player to choose the resources for the initial benefits
     * @param nickname recipient of the message
     * @param player is the position of the player in the playing order
     * @param amount is the amount of resources to choose
     * @return the int corresponding to the chosen resources
     */
    public int chooseResource(String nickname, String player, int amount) {
        ClientHandler client=namesToClient.get(nickname);
        if(player.equals("fourth") && amount==2){
            client.send(new RequestInt("RESOURCE","Please choose another resource."));
        } else {
            client.send(new RequestInt("RESOURCE","You are the " + player + " player. Please choose a resource."));
        }
        return waitForInt(client);
    }

    /**
     * Asks the player which of the four initial leader he wants to discard
     * @param nickname recipient of the message
     * @param number is the the number of leader already discarded
     * @param IdLeader contains the IDs of the leader of the player
     * @return the ID of the discarded leader
     */
    public int discardFirstLeaders(String nickname, int number, ArrayList<Integer> IdLeader) {
        ClientHandler client=namesToClient.get(nickname);
        if(number==1) {
            client.send(new SendMessage("Please choose the first leader card to discard."));
        } else {
            client.send(new SendMessage("Please choose the second leader card to discard."));
        }
        client.send(new PassLeaderCard(IdLeader));
        return waitForInt(client);
    }

    /**
     * Informs the player that the game is starting
     * @param nickname recipient of the message
     */
    public void startGame(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new SendMessage("The game starts!"));
    }

    /**
     * Sends the faith path to the player
     * @param nickname recipient of the message
     * @param player the owner of the faith path
     * @param path the faith path
     * @param SinglePlayer indicates if the match is Single player
     */
    public void seeFaithPath(String nickname, String player, FaithPath path, boolean SinglePlayer) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new FaithPathInfo(("\n\n"+Constants.DIVIDING_LINE+"\nThis is the Dashboard of "+Constants.ANSI_GREEN+player+Constants.ANSI_RESET+" :\n"), path, SinglePlayer));
    }

    /**
     * Sends the storage to the player
     * @param nickname recipient of the message
     * @param playerDashboard is the playerDashboard sent
     * @param dashboardOwner is the owner of the dashboard
     * @param toPrint indicates if the storage need to be printed in the CLI
     * @param vaultUpdate if it contains also the vault
     */
    public void seeStorage(String nickname, PlayerDashboard playerDashboard ,String dashboardOwner, boolean toPrint, boolean vaultUpdate) {
        ClientHandler client=namesToClient.get(nickname);
        Storage storage = playerDashboard.getStorage();
        Vault vault;
        if(vaultUpdate) {
            vault = playerDashboard.getVault();
        }
        else {
            vault = null;
        }
        LeaderCard leader1 = playerDashboard.getLeaders().get(0);
        LeaderCard leader2 = playerDashboard.getLeaders().get(1);
        client.send(new StorageInfo(storage, vault, leader1, leader2, dashboardOwner, toPrint));
    }

    /**
     * Sends the DevCards Space to the player
     * @param nickname recipient of the message
     * @param space the development Cards Space
     */
    public void seeDevCardsSpace(String nickname, DevCardsSpace space) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new DevCardsSpaceInfo(space));
    }

    /**
     * Sends the actionToken
     * @param nickname recipient of the message
     * @param actionToken is the action token
     */
    public void seeActionToken(String nickname, ActionToken actionToken){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new ActionTokenInfo(actionToken));
    }

    /**
     * Ask the player what he want to see of the gameBoard
     * @param first if the player is the first
     * @param nickname recipient of the message
     * @return the number corresponding to what the player wants to see
     */
    public int seeGameBoard(boolean first, String nickname){
        ClientHandler client=namesToClient.get(nickname);
        if(first) notifyTurnChoice(nickname, " is watching the game board");
        client.send(new RequestInt("GAMEBOARD","What do you want to see about the Game Board?"));
        return waitForInt(client);
    }

    /**
     * Sends to the player the leader cards
     * @param nickname recipient of the message
     * @param leaderCards the IDs of the leader cards
     * @return if the player wants to see more or not
     */
    public int seeLeaderCards(String nickname, ArrayList<Integer> leaderCards){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new SeeLeaderCards(leaderCards));
        return waitForInt(client);
    }

    /**
     * Sends to the player the market
     * @param nickname recipient of the message
     * @param market the market
     * @return if the player wants to see more or not
     */
    public int seeMarket(String nickname, Market market){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new SeeMarket(market));
        return waitForInt(client);
    }

    /**
     * Ask the player the line of the market that he wants to choose
     * @param nickname recipient of the message
     * @return the chosen line
     */
    public int chooseLine(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("LINE","Please choose what you want to see from the Development Cards Grid"));
        return waitForInt(client);
    }

    /**
     * Sends to the player the grid of the development cards
     * @param nickname  recipient of the message
     * @param id the IDs of the cards
     * @return if the player wants to see more or not
     */
    public int seeGrid(String nickname, ArrayList<Integer> id){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new SeeGrid(id));
        return waitForInt(client);
    }

    /**
     * Sends to the player the production
     * @param nickname recipient of the message
     * @param productions the production
     * @return the production that the player intends to do
     *
     */
    public int seeProduction(String nickname, ArrayList<Integer> productions) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new SeeProductions(productions));
        return waitForInt(client);
    }

    /**
     * Sends the leader card of the other players
     * @param nickname recipient of the message
     * @param player is the owner of the leader
     * @param leaderCards the leader cards
     */
    public void seeOtherLeader(String nickname, String player, ArrayList<Integer> leaderCards) {
        ClientHandler client=namesToClient.get(nickname);
        if(leaderCards.isEmpty()) {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has no active leader cards"));
        } else {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has these active leader cards:"));
            client.send(new SeeOtherCards(leaderCards));
        }
    }

    /**
     * Sends the the development Cards of the other players
     * @param nickname recipient of the message
     * @param player the owner of the cards
     * @param devCards the IDs of the dev Cards
     */
    public void seeOtherDev(String nickname, String player, ArrayList<Integer> devCards) {
        ClientHandler client=namesToClient.get(nickname);
        if(devCards.isEmpty()) {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has no development card that he can use"));
        } else {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has these development cards that he can use:"));
            client.send(new SeeOtherCards(devCards));
        }
    }

    /**
     * Asks if he wants to see more or not
     * @param nickname recipient of the message
     * @return the answer of the player
     */
    public int askChoice(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("CHOICE", null));
        return waitForInt(client);
    }

    /**
     * Asks the player what he wants to do in his turn
     * @param nickname recipient of the message
     * @return the answer of the player
     */
    public int chooseTurn(String nickname){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("TURN","Choose what you want to do in this turn:"));
        return waitForInt(client);
    }

    /**
     * Asks the player which leader he wants to activate
     * @param nickname recipient of the message
     * @param leaders the IDs of the leader
     * @return the answer of the player
     */
    public int activeLeader(String nickname, ArrayList<Integer> leaders){
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is activating a leader");
        client.send(new ActiveLeader("Which leader do you want to activate?", leaders));
        return waitForInt(client);
    }

    /**
     * Asks the player which player he wants to discard
     * @param nickname recipient of the message
     * @param leaders IDs of the leader
     * @return the answer of the player
     */
    public int discardLeader(String nickname, ArrayList<Integer> leaders){
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is discarding a leader");
        client.send(new DiscardLeader("Which leader do you want to discard?", leaders));
        return waitForInt(client);
    }

    /**
     * Ask the player if he wants to reorganize the storage
     * @param number is the number of time that the player hs reorganized the storage
     * @param nickname recipient of the message
     * @return the answer of the player
     */
    public int manageStorage(int number, String nickname){
        ClientHandler client=namesToClient.get(nickname);
        if(number==1){
            notifyTurnChoice(nickname, " is managing his storage");
            client.send(new RequestInt("STORAGE","Before using the market do you want to reorder your storage? You won't be able to do it later."));
        }
        else
            client.send(new RequestInt("STORAGE","Do you want to make other changes to the storage?"));
        return waitForInt(client);
    }

    /**
     * Updates the gui when a player actives a leader cards so it is visible for all
     * @param player owner of the card
     * @param idCard ID of the activated card
     * @param nickname recipient of the message
     * @param pos position of the leader card
     */
    public void activeOtherLeaderCard(String player, int idCard, String nickname, int pos) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new OtherLeaderCard(idCard, "ACTIVE", player, pos));
    }

    /**
     * Updates the gui when a player discard a leader cards so it is visible for all
     * @param player owner of the card
     * @param idCard ID of the discarded card
     * @param nickname recipient of the message
     * @param pos position of the leader card
     */
    public void discardOtherLeaderCard(String player, int idCard, String nickname, int pos) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new OtherLeaderCard(idCard, "DISCARD", player, pos));
    }

    /**
     * Asks the player which shelves he wants to reverse
     * @param nickname  recipient of the message
     * @return the answer of the player
     */
    public ArrayList<Integer> moveShelves(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestDoubleInt("SHELVES","Which shelves do you want to reverse?"));
        return waitForDoubleInt(client);
    }

    /**
     * Asks the player which line of the market he wants
     * @param nickname recipient of the message
     * @return the answer of the player
     */
    public int useMarket(String nickname){
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is using the market");
        client.send(new RequestInt("MARKET","Which market line do you want?"));
        return waitForInt(client);
    }

    /**
     * Sends to the player the selected balls from the market
     * @param nickname recipient of the message
     * @param balls the selected balls
     * @return where he wants to place the selected resources
     */
    public ArrayList<Integer> seeBall(String nickname, ArrayList<Ball> balls) {
        ClientHandler client=namesToClient.get(nickname);
        ArrayList<Integer> moves=new ArrayList<>();
        client.send(new SeeBall(balls));
        moves.add(waitForInt(client));

        if(moves.get(0) != -1) {
            client.send(new RequestInt("SHELF", ""));
            moves.add(waitForInt(client));
        }
        return moves;
    }

    /**
     * Asks the player which one of the white ball leader he want to use if he has two white ball leader activated
     * @param nickname recipient of the message
     * @return the answer of the player
     */
    public int askWhiteBallLeader(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("WHITE","You have 2 active WhiteBall leaders, which one do you want to use in this turn? (1 or 2)"));
        return waitForInt(client);
    }

    /**
     * Ask the player which card he wants to buy
     * @param nickname recipient of the message
     * @param cards the IDs of the cards
     * @param spaces the spaces in which he can place the card
     * @return the answer of the player
     */
    public ArrayList<Integer> askCardToBuy(String nickname, ArrayList<Integer> cards, ArrayList<Integer> spaces) {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is buying a card");
        client.send(new RequestDoubleInt("DEVCARD", null, cards, spaces));
        return waitForDoubleInt(client);
    }

    /**
     * Asks the player in which space he wants to place the card
     * @param nickname recipient of the message
     * @return the answer of the player
     */
    public int askSpace(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("SPACE","Choose the space where to insert the card"));
        return waitForInt(client);
    }


    /**
     * Sends a RequestInt message asking to select the desired production type
     * @param nickname recipient of the message
     * @return player's choice
     */
    public int askType(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is activating a production");
        client.send(new RequestInt("TYPE","Choose productions\n1) Basic Production\n2) Development Card\n3) Leader Card\n4) It's okay, do productions"));
        return waitForInt(client);
    }


    /**
     * Sends a RequestInt message asking to select the input resource for production
     * @param nickname recipient of the message
     * @return player's choice
     */
    public Resource askInput(String nickname){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("INPUT","Choose the input:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT"));
        number = waitForInt(client);
        switch(number) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.STONE;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.SERVANT;
            default : return null;
        }
    }


    /**
     * Sends a RequestInt message asking to select the output resource for production
     * @param nickname recipient of the message
     * @return player's choice
     */
    public Resource askOutput(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("OUTPUT","Choose the output:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT"));
        number = waitForInt(client);
        switch(number) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.STONE;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.SERVANT;
            default : return null;
        }
    }


    /**
     * Sends a BasicProductionInfo message
     * @param nickname recipient of the message
     * @param input1 basic production first input
     * @param input2 basic production second input
     * @param output basic production output
     */
    public void sendBasicProduction(String nickname, Resource input1, Resource input2, Resource output) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new BasicProductionInfo(input1, input2, output));
    }


    /**
     * Sends a RequestInt message asking to select the space of the development card that the player wants to use for productions
     * @param nickname recipient of the message
     * @return player's choice
     */
    public int askDevCard(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("DEVCARD","Insert the number of the space containing the development card"));
        return waitForInt(client);
    }


    /**
     * Sends an InitializeGameBoard message
     * @param crashed indicates if the message is used to restore the game board of a crashed player
     * @param nickname recipient of the message
     * @param market is the player's market
     * @param idCards are the cards IDs of the grid
     * @param leader leaders belonging to the player
     * @param active1 indicates if the first leader of the player is active
     * @param discarded1 indicates if the first leader of the player is discarded
     * @param active2 indicates if the second leader of the player is active
     * @param discarded2 indicates if the first leader of the player is discarded
     */
    public void initializeGameBoard(boolean crashed, String nickname, Market market, ArrayList<Integer> idCards, ArrayList<Integer> leader, boolean active1, boolean discarded1, boolean active2, boolean discarded2) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new InitializeGameBoard(crashed, market, idCards, leader, active1, discarded1, active2, discarded2));

    }

    /**
     * Sends an UpdateFaithPath message
     * @param nickname recipient of the message
     * @param nickToUpdate is the owner of the updated faith path
     * @param nickPos is the new position in the path
     * @param isLorenzo indicates if the faith path modification concerns Lorenzo il Magnifico's path
     */
    public void updateFaithPath(String nickname, String nickToUpdate, int nickPos, boolean isLorenzo){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new UpdateFaithPath(nickToUpdate, nickPos, isLorenzo));
    }

    /**
     * Sends an UpdatePapalPawn message
     * @param nickname recipient of the message
     * @param nickToUpdate is the player who activated a papal pawn
     * @param pawn is the level of the activated papal pawn
     */
    public void updatePapalPawn(String nickname, String nickToUpdate, int pawn){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new UpdatePapalPawn(nickToUpdate, pawn));
    }

    /**
     * Sends a PlayersInfo message with all the nicknames of the lobby
     * @param nickname recipient of the message
     * @param playerNumber is the number of player of the lobby
     * @param nicknames are all the player's nickname of the lobby
     */
    public void initialInfo(String nickname, int playerNumber, ArrayList<String> nicknames){
        PlayersInfo playersInfo = new PlayersInfo(playerNumber);
        for(String nick: nicknames){
            playersInfo.addNick(nick);
        }
        ClientHandler client = namesToClient.get(nickname);
        client.send(playersInfo);
    }


    /**
     * Sends a RequestInt message asking the player which production leader wants to use
     * @param nickname recipient of the message
     * @return player's choice
     */
    public int askLeaderCard(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("LEADCARD","Insert the number of the production leader that you want to use"));
        return waitForInt(client);
    }


    /**
     * Sends a SendMessage notifying that a vatican report has been activated, and the players that received points
     * @param nicknames recipient of the message
     */
    public void papalPawn(ArrayList<String> nicknames) {
        for(ClientHandler client : namesToClient.values()) {
            if(clientConnected.get(client.getNickname())) {
                client.send(new SendMessage("A Vatican report was activated. The following players will get the points of the Pope's Favor tile:"));
                for (String s : nicknames) {
                    client.send(new SendMessage(Constants.ANSI_BLUE + s + Constants.ANSI_RESET));
                }
            }
        }
    }


    /**
     * Sends a RequestInt for the end turn, asking if the player wants to active a leader, to discard a leader or to finish his turn
     * @param nickname recipient of the message
     * @return player's choice
     */
    public int endTurn(String nickname){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new RequestInt("END","What do you want to do?\n1) Active Leader\n2) Discard Leader\n3) End turn"));
        return waitForInt(client);
    }


    /**
     * Sends a Win message
     * @param nickname recipient of the message
     * @param victoryPoints points accumulated by the player
     */
    public void win(String nickname, int victoryPoints) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new Win("You won! You have "  + victoryPoints  + " victory points\n"));
    }


    /**
     * Sends a Lose message
     * @param nickname recipient of the message
     * @param victoryPoints points accumulated by the player
     */
    public void lose(String nickname, int victoryPoints) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new Lose("Sorry, you lose. You have " + victoryPoints + " victory points\n"));
    }


    /**
     * Sends CardsSpaceInfo message used to
     * @param player recipient of the message
     * @param nickname is the player who modified his cards spaces
     * @param level is the level of the card
     * @param space is the card space
     * @param idCard is the ID of the new card
     */
    public void updateDevCardSpace(String player, String nickname, int level, int space, int idCard) {
        ClientHandler client=namesToClient.get(player);
        client.send(new CardsSpaceInfo(nickname, level, space, idCard));
    }

    /**
     * Sends a GridInfo message
     * @param player recipient of the message
     * @param idCards are the IDs of the card in the grid
     */
    public void updateGrid(String player, ArrayList<Integer> idCards) {
        ClientHandler client=namesToClient.get(player);
        client.send(new GridInfo(idCards));
    }

    /**
     * Sends ErrorMessage
     * @param nickname recipient of the message
     * @param errorType indicates the error type
     */
    public void sendErrorMessage(String nickname, String errorType){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new ErrorMessage(errorType));
    }

    /**
     * Sends an error message for invalid input
     * @param nickname recipient of the message
     */
    public void sendInvalidInput(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new SendMessage("INVALID"));
    }


    /**
     * Sends ResetCard message to the selected client
     * @param nickname recipient of the message
     * @param pos position of the card that needs to be reset
     */
    public void resetCard(String nickname, int pos) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new ResetCard(pos));
    }

    /**
     * Sends the end game message
     */
    public void closeConnection() {
        for(ClientHandler client : namesToClient.values()) {
            client.isEnd(true);
            client.send(new SendMessage("END_GAME"));
        }
    }

    /**
     * Calls the server removal of all players
     */
    public void allClientCrashed() {
        for(ClientHandler client : namesToClient.values()) {
            server.clientDisconnected(client.getNickname());
        }
    }

    /**
     * This method closes the game of the selected player
     * @param nickname is the player whose game will be closed
     */
    public void closeGame(String nickname) {
        server.closeGame(nickname);
    }


    /**
     * Sends the turn status to the player
     * @param message indicates the turn status
     * @param nickname recipient of the message
     */
    public void sendTurnStatus(String message, String nickname){
        if(message.equals("START")){
            notifyPlayingNick(nickname);
        }
        ClientHandler client = namesToClient.get(nickname);
        client.send(new TurnStatus(message));
    }

    /**
     * Sends MarketInfo message with the updated market
     * @param nickname recipient of the message
     * @param market is the updated market
     */
    public void sendUpdateMarket(String nickname, Market market){
        ClientHandler client = namesToClient.get(nickname);
        client.send(new MarketInfo(market));
    }

    /**
     * Sends a CardsSpaceInfo message to the crashed player, after reconnection
     * @param nickname recipient of the message
     * @param space all the development cards spaces that need to be restored after reconnection
     * @param owner reconnected client
     */
    public void setDevCardsSpaceForReconnection(String nickname, ArrayList<DevelopmentCardDeck> space, String owner) {
        ClientHandler client = namesToClient.get(nickname);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < space.get(i).size(); j++){
                client.send(new CardsSpaceInfo(owner,j+1,i+1,space.get(i).getDeck().get(j).getCardID()));
            }
        }
    }
}
