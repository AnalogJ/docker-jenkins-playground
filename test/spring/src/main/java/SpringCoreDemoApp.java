import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class SpringCoreDemoApp {
    // This will be created and injected by spring IoC
    private HelloWorld hello;

    public HelloWorld getHello() {
        return hello;
    }

    public void setHello(HelloWorld hello) {
        this.hello = hello;
    }

    public static void main(String[] args) {
        BeanFactory beanFactory = getBeanFactory();

        SpringCoreDemoApp app = beanFactory.getBean("app", SpringCoreDemoApp.class);
        app.runApp();
    }

    public void runApp() {
        hello.sayHello();
    }

    public static BeanFactory getBeanFactory() {
        DefaultListableBeanFactory xbf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(xbf);
        reader.loadBeanDefinitions(new ClassPathResource("beans.xml", SpringCoreDemoApp.class));
        return xbf;
    }

}
