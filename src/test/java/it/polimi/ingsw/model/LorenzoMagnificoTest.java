package it.polimi.ingsw.model;

import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.LorenzoMagnifico;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LorenzoMagnificoTest{

    @Test
    public void testDraw() {
        LorenzoMagnifico lorenzoMagnifico=new LorenzoMagnifico();

        ActionToken element=lorenzoMagnifico.draw();
        assertEquals(element, lorenzoMagnifico.getTokens().get(lorenzoMagnifico.getTokens().size()-1));
    }
}