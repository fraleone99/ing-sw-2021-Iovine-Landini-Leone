package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.card.leadercard.StorageLeader;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.Storage;
import it.polimi.ingsw.model.gameboard.playerdashboard.Vault;
import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing information about player's storage, vault and storage leaders.
 *
 * @author Lorenzo Iovine
 */
public class StorageInfo implements Answer {
    String nickname;

    //STORAGE
    private final Resource shelf1Type;
    private final Resource shelf2Type;
    private final Resource shelf3Type;

    private final int shelf1Amount;
    private final int shelf2Amount;
    private final int shelf3Amount;

    //VAULT
    private final int coinsAmount;
    private final int servantsAmount;
    private final int stoneAmount;
    private final int shieldsAmount;

    //STORAGE LEADER
    private final int storageLeader1;
    private  Resource leader1Type;
    private final int storageLeader2;
    private  Resource leader2Type;

    private final boolean vaultUpdate;
    private final boolean toPrint;


    @Override
    public Object getMessage() {
        return null;
    }

    public StorageInfo(Storage storage, Vault vault, LeaderCard leader1, LeaderCard leader2, String nickname, boolean toPrint) {
        this.shelf1Type = storage.getTypeShelf(1);
        this.shelf2Type = storage.getTypeShelf(2);
        this.shelf3Type = storage.getTypeShelf(3);
        this.shelf1Amount = storage.getAmountShelf(1);
        this.shelf2Amount = storage.getAmountShelf(2);
        this.shelf3Amount = storage.getAmountShelf(3);
        if(vault!=null) {
            vaultUpdate = true;
            this.coinsAmount = vault.getResource(Resource.COIN);
            this.servantsAmount = vault.getResource(Resource.SERVANT);
            this.stoneAmount = vault.getResource(Resource.STONE);
            this.shieldsAmount = vault.getResource(Resource.SHIELD);
        } else {
            coinsAmount=-1;
            servantsAmount=-1;
            stoneAmount=-1;
            shieldsAmount=-1;
            vaultUpdate = false;
        }

        if(leader1 instanceof StorageLeader) {
            storageLeader1 = ((StorageLeader) leader1).getAmount();
            leader1Type = ((StorageLeader) leader1).getType();
        }
        else
            storageLeader1 = -1;

        if(leader2 instanceof StorageLeader) {
            storageLeader2 = ((StorageLeader) leader2).getAmount();
            leader2Type = ((StorageLeader) leader2).getType();
        }
        else
            storageLeader2 = -1;

        this.nickname = nickname;
        this.toPrint = toPrint;
    }

    public Resource getShelf1Type() {
        return shelf1Type;
    }

    public Resource getShelf2Type() {
        return shelf2Type;
    }

    public Resource getShelf3Type() {
        return shelf3Type;
    }

    public int getShelf1Amount() {
        return shelf1Amount;
    }

    public int getShelf2Amount() {
        return shelf2Amount;
    }

    public int getShelf3Amount() {
        return shelf3Amount;
    }

    public int getCoinsAmount() {
        return coinsAmount;
    }

    public int getServantsAmount() {
        return servantsAmount;
    }

    public int getStoneAmount() {
        return stoneAmount;
    }

    public int getShieldsAmount() {
        return shieldsAmount;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isVaultUpdate() {
        return vaultUpdate;
    }

    public boolean isToPrint() {
        return toPrint;
    }

    public int getStorageLeader1() {
        return storageLeader1;
    }

    public int getStorageLeader2() {
        return storageLeader2;
    }

    public Resource getLeader1Type() {
        return leader1Type;
    }

    public Resource getLeader2Type() {
        return leader2Type;
    }
}
