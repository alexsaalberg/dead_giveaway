
package deadgiveaway.server;

import java.util.*;
import ocsf.server.*;
import static org.mockito.Mockito.*;
import deadgiveaway.*;
import java.io.*;
import org.junit.*;

/**
 * Writes specified seeds to a file with what cards are generated using it.
 * @author Austin Sparks
 */
public class SeedMatrix
{
    
    /**
    * Creates a list of seeds and the card values they bring
    * @author Austin Sparks
    */
    @Test
    public void testSeeds() throws IOException
    {
        PrintWriter out = new PrintWriter(new File("system/seedmatrix.txt"));
        ConnectionToClient conn = mock(ConnectionToClient.class);
        //final int[] seeds = {1, 2, 45, 47, 49};
        ArrayList<Integer> seeds = new ArrayList<Integer>();
        for (int i = 1; i < 50; i++)
            seeds.add(i);
        for (int seed : seeds)
        {
            ClueServer server = new ClueServer(555, seed, new ClueServerHelper(false, false, false));
            server.clientConnected(conn);
            server.start(true);
            out.print("Seed: " + seed + "\n");
            ArrayList<Player> players = server.getPlayers();
             
            int i = 0;
            for (Player pl : players)
            {
                i++;
                out.print("player " + i + ":\n");
                out.print("\tClue Cards\n\t\t:");
                Card[] clueCards = pl.getClueCards();
                ActionCard[] actionCards = pl.getActionCards();
                for (Card c : clueCards)
                {
                    if (c != null) 
                    {
                        printCard(c, out);
                    }
                }
                out.print("\n");

                out.print("\tActionCards:\n");
                for (ActionCard c : actionCards)
                {
                    if (c != null) 
                    {
                        out.print("\t\t" + c.getType() + "\n");
                    }
                }
            }

            ArrayList<Card> solution = server.getSolution();
            out.print("\tSolution:\n\t\t");
            for (Card c : solution)
            {
                if (c != null) 
                {
                    printCard(c, out);
                }
            }
            out.print("\n");

            ArrayList<ActionCard> deck = server.getDeck();
            out.print("\tDeck:\n\t\t");
            for (Card c : deck)
                printCard(c, out);
            out.print("\n\n\n");
            out.flush();
        }
    }

    private void printCard(Card c, PrintWriter out) throws IOException
    {
        String msg;

        if (c instanceof LocationCard)
        {
            msg = ((LocationCard)c).getTitle().toString();
        }
        else if (c instanceof SuspectCard)
        {
           msg = ((SuspectCard)c).getName().toString();
        }
        else if (c instanceof VehicleCard)
        {
            msg = ((VehicleCard)c).getModel().toString();
        }
        else
        {
            msg = ((ActionCard)c).getType().toString();
        }

        out.print(msg + ", ");
    }
	   
}
