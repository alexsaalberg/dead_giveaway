package deadgiveaway.fake;

import deadgiveaway.client.UserInterface;

/**
  * The FakeClueClientJon class provides methods and fields required for 
  * testing of the ClientApp class. 
  * 
  * @author Jon Kuzmich
  * @version 2.0
  */
public class FakeClueClientJon
{
    /**
     * Constructs a new instance of a FakeClueClientJon.
     * @param hostname The name of the host to connect to.
     * @param port The port to connect to on the host machine.
     * @param interfaceChoice The choice of what UI to use.
     * @param interfaceType the type of interface to use
     * card.
     */
    public FakeClueClientJon(String hostname, int port, 
            UserInterface interfaceChoice,
            Class<? extends UserInterface> interfaceType)
    {
        String interfaceName = interfaceType.getName();
        
        System.out.println(hostname + " " + port + " " + interfaceName);
    }
}