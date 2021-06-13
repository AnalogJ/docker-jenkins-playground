import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HelloWorld {
    public static final Log LOG = LogFactory.getLog(HelloWorld.class);

    public void sayHello() {
        LOG.info("Hello World!");
    }
}
