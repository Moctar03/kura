package org.eclipse.kura.linux.net.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.core.linux.executor.LinuxExitStatus;
import org.junit.Test;
import org.eclipse.kura.net.dhcp.DhcpLease;
import org.eclipse.kura.linux.net.util.DhcpLeaseTool;
import org.eclipse.kura.executor.CommandStatus;
import org.eclipse.kura.executor.Pid;
import org.eclipse.kura.executor.Signal;
import org.eclipse.kura.executor.Command;
import org.eclipse.kura.executor.CommandExecutorService;


public class DhcpLeaseToolTest {
    
    protected static final CommandStatus successStatus = new CommandStatus(new Command(new String[] {}),
            new LinuxExitStatus(0));
    
    static private final int DHCP_LEASES = 4;
    
    private String getDhcpLeaseInfo() {
        return   "MAC a8:6d:aa:0b:53:74 IP 172.16.1.100 HOSTNAME DESKTOP-4E3ACHI BEGIN 2021-12-17 18:19:13 END 2021-12-17 20:19:13 MANUFACTURER Intel Corporate\n"
               + "MAC bc:a8:a6:9a:0f:ba IP 172.16.1.101 HOSTNAME N-20HEPF0UV1B5 BEGIN 2021-12-17 18:35:42 END 2021-12-17 20:35:42 MANUFACTURER Intel Corporate\n"
               + "MAC cd:fg:a7:54:1f:78 IP 172.16.1.102 HOSTNAME DELLGEEK BEGIN 2021-12-17 19:26:55 END 2021-12-17 21:25:22 MANUFACTURER Intel Corporate\n"
               + "MAC a9:bc:c8:88:bb:45 IP 172.16.1.103 HOSTNAME TECHTERMS BEGIN 2021-12-17 19:47:25 END 2021-12-17 21:35:43 MANUFACTURER Intel Corporate\n";
    }
    
    @Test
    public void checkDhcpLease() throws KuraException {
        String commandOutput = getDhcpLeaseInfo();
        CommandExecutorServiceStub executorServiceStub = new CommandExecutorServiceStub(successStatus);
        executorServiceStub.writeOutput(commandOutput);
        List<DhcpLease> leases = DhcpLeaseTool.probeLeases(executorServiceStub);
        assertEquals(leases.size(), DHCP_LEASES);
        for (DhcpLease x : leases) {
            System.out.println(x.toString());
        }
        assertEquals(leases.get(0).getMacAddress(), "a8:6d:aa:0b:53:74");
        assertEquals(leases.get(0).getIpAddress(), "172.16.1.100");
        assertEquals(leases.get(0).getHostname(), "DESKTOP-4E3ACHI");
        assertEquals(leases.get(1).getMacAddress(), "bc:a8:a6:9a:0f:ba");
        assertEquals(leases.get(1).getIpAddress(), "172.16.1.101");
        assertEquals(leases.get(1).getHostname(), "N-20HEPF0UV1B5");
        assertEquals(leases.get(2).getMacAddress(), "cd:fg:a7:54:1f:78");
        assertEquals(leases.get(2).getIpAddress(), "172.16.1.102");
        assertEquals(leases.get(2).getHostname(), "DELLGEEK");
        assertEquals(leases.get(3).getMacAddress(), "a9:bc:c8:88:bb:45");
        assertEquals(leases.get(3).getIpAddress(), "172.16.1.103");
        assertEquals(leases.get(3).getHostname(), "TECHTERMS");
    }
}
