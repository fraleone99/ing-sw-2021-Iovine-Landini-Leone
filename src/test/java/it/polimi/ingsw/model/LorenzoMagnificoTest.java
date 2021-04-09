package it.polimi.ingsw.model;

import junit.framework.TestCase;

public class LorenzoMagnificoTest extends TestCase {

    public void testDraw() {
        LorenzoMagnifico lorenzoMagnifico=new LorenzoMagnifico();

        ActionToken element=lorenzoMagnifico.draw();
        assertEquals(element, lorenzoMagnifico.getTokens().get(lorenzoMagnifico.getTokens().size()-1));
    }
}