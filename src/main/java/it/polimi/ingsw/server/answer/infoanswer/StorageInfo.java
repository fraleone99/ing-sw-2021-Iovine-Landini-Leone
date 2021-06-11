package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.Storage;
import it.polimi.ingsw.model.gameboard.playerdashboard.Vault;
import it.polimi.ingsw.server.answer.Answer;

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

    private boolean vaultUpdate;


    @Override
    public Object getMessage() {
        return null;
    }

    public StorageInfo(Storage storage, Vault vault, String nickname) {
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

        this.nickname = nickname;
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
}
