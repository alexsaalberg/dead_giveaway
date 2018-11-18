import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import deadgiveaway.*;
import deadgiveaway.server.*;


@RunWith(Suite.class)

@Suite.SuiteClasses({
    ActionCardTest.class, 
    LocationCardTest.class,
    SuspectCardTest.class,
    VehicleCardTest.class,
    ClueServerHandleMessageFromClientTest.class,
    ClueServerIntegrationTest.class,
    ClueServerTest.class,
    RobotPlayerIntegrationTest.class,
    RobotPlayerTest.class,
    ServerAppTest.class
})

/**
 * @author Alex Saalberg
 */
public class MainTestSuite {
}
