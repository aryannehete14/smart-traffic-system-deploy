package SmartTrafficSystem.project;

import SmartTrafficSystem.project.service.TrafficService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.awt.GraphicsEnvironment;
import java.awt.EventQueue;

@SpringBootApplication
public class SmartTrafficApplication {
    public static void main(String[] args) {
        // Start Spring Boot normally
        ConfigurableApplicationContext ctx = SpringApplication.run(SmartTrafficApplication.class, args);

        // ONLY launch the UI if a screen is actually available (Local laptop)
        // This prevents crashing on Railway/Cloud servers
        if (!GraphicsEnvironment.isHeadless()) {
            EventQueue.invokeLater(() -> {
                TrafficUI ui = new TrafficUI();
                TrafficService service = ctx.getBean(TrafficService.class);
                ui.setTrafficService(service);
                ui.initUI();
            });
        } else {
            System.out.println("Running in Headless mode (Server). Desktop UI will not launch.");
        }
    }
}