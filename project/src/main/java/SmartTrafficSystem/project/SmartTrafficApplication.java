package SmartTrafficSystem.project;

import SmartTrafficSystem.project.service.TrafficService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.awt.EventQueue;

@SpringBootApplication
public class SmartTrafficApplication {
    public static void main(String[] args) {
        // 1. Start Spring Boot with headless mode DISABLED
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(SmartTrafficApplication.class)
                .headless(false)
                .run(args);

        // 2. Launch the Desktop UI on the Event Dispatch Thread
        EventQueue.invokeLater(() -> {
            TrafficUI ui = new TrafficUI();
            
            // Manually inject the Service from the Spring Context
            TrafficService service = ctx.getBean(TrafficService.class);
            ui.setTrafficService(service); 
            
            // Start the window
            ui.initUI();
        });
    }
}