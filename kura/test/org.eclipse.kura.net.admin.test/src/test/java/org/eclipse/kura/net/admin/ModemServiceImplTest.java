package org.eclipse.kura.net.admin;

import static org.junit.Assert.assertEquals;

import org.eclipse.kura.net.admin.modem.quectel.generic.QuectelGeneric;
import org.eclipse.kura.net.admin.modem.hspa.HspaModem;
import org.eclipse.kura.KuraException;
import org.junit.Test;
import java.util.List;

public class ModemServiceImplTest {

    private String modemInfoOutput;
    private String modemObjectInfo;
    private HspaModem hm;
    private QuectelGeneric qg;
    private String[] modemInfoList;

    
    @Test
    public void checkQueryNetworkInformation() throws KuraException {
        givenModemInfoOutput("+QNWINFO: \"EDGE\",\"20810\",\"GSM 900\",117\"");
        
        whenModemObjectInfo("QuectelGeneric");
        
        thenModemQueryNetworkInformation();
    }
    
    @Test
    public void checkExtendedRegistrationStatus() throws KuraException {
        givenModemInfoOutput("+CGREG: 2,1,\"B5AB\",\"D747\",0");
        
        whenModemObjectInfo("QuectelGeneric");
        
        thenModemExtendedRegistrationStatus();
    }
    
    @Test
    public void checkRegisteredNetwork() throws KuraException {
        givenModemInfoOutput("+QSPN: \"F SFR\",\"SFR\",\"\",0,\"20810\"");
        
        whenModemObjectInfo("QuectelGeneric");
        
        thenModemRegisteredNetwork();
    }
    
    @Test
    public void checkHspaExtendedRegistrationStatus() throws KuraException {
        givenModemInfoOutput("+CGREG: 2,1,B5AB,D747,0");
        
        whenModemObjectInfo("HspaModem");
        
        thenModemExtendedRegistrationStatus();
    }
    
    @Test
    public void checkHspaRegisteredNetwork() throws KuraException {
        givenModemInfoOutput("+COPS: 0,1,\"SFR\",2");
        
        whenModemObjectInfo("HspaModem");
        
        thenModemRegisteredNetwork();
    }
    
    public void givenModemInfoOutput(String modemInfoOutput) {
        this.modemInfoOutput = modemInfoOutput;
    }
    
    public void whenModemObjectInfo(String modemObjectInfo) {
        this.modemObjectInfo = modemObjectInfo;
        if (modemObjectInfo == "HspaModem") {
            this.hm = new HspaModem(null, null, null);
        } else if (modemObjectInfo == "QuectelGeneric") {
            this.qg = new QuectelGeneric(null, null, null);
        }
    }
    
    public void thenModemExtendedRegistrationStatus() throws KuraException {
        if (modemObjectInfo == "HspaModem") {
            modemInfoList = hm.getExtendedRegistrationStatusReply(modemInfoOutput);
            assertEquals(modemInfoList.length, 5);
            assertEquals(hm.getLAC(), "46507");
            assertEquals(hm.getCI(), "55111");
        } else if (modemObjectInfo == "QuectelGeneric") {
            modemInfoList = qg.getExtendedRegistrationStatusReply(modemInfoOutput);
            assertEquals(modemInfoList.length, 5);
            assertEquals(qg.getLAC(), "46507");
            assertEquals(qg.getCI(), "55111");
        }
    }
    
    public void thenModemQueryNetworkInformation() throws KuraException {
        if (modemObjectInfo == "QuectelGeneric") {
            modemInfoList = qg.getQueryNetworkInformationReply(modemInfoOutput);
            assertEquals(modemInfoList.length, 4);
            assertEquals(qg.getRadio(), "EDGE");
            assertEquals(qg.getBand(), "GSM 900");
        }
    }
    
    public void thenModemRegisteredNetwork() throws KuraException {
        if (modemObjectInfo == "HspaModem") {
            modemInfoList = hm.getRegisteredNetworkReply(modemInfoOutput);
            assertEquals(modemInfoList.length, 4);
            assertEquals(hm.getNetworkName(), "SFR");
        } else if (modemObjectInfo == "QuectelGeneric") {
            modemInfoList = qg.getRegisteredNetworkReply(modemInfoOutput);
            assertEquals(modemInfoList.length, 5);
            assertEquals(qg.getNetworkName(), "F SFR");
            assertEquals(qg.getPLMNID(), "20810");
        }
    }
}
