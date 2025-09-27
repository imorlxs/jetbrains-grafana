package internship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grafana.foundation.dashboard.Dashboard;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final String OUTPUT_FILENAME = "cpu-usage-generated.json";

    public static void main(String[] args) {
        CpuDashboardGenerator generator = new CpuDashboardGenerator();
        try {
            Dashboard dashboard = generator.generateDashboard();

            String jsonOutput;

            // GrafanaCTL requires dashboards to be wrapped in a Kubernetes style wrapper.
            if (args.length > 0 && args[0].equalsIgnoreCase("--grafanactl")) {
                DashboardWrapper wrapper = new DashboardWrapper(
                        "dashboard.grafana.app/v1beta1",
                        "Dashboard",
                        new Metadata("my-dashboard"),
                        dashboard
                );
                ObjectMapper mapper = new ObjectMapper();
                jsonOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);
            }
            else {
                jsonOutput = dashboard.toJSON();
            }

            Path outputPath = Paths.get(OUTPUT_FILENAME);

            try (FileWriter fileWriter = new FileWriter(outputPath.toFile())) {
                fileWriter.write(jsonOutput);
                System.out.println("Dashboard successfully written to: " + outputPath.toAbsolutePath());
            }
            // Catch I/O error during file writing
            catch (IOException ioException) {
                System.err.println("Error writing JSON to file '" + OUTPUT_FILENAME + "': " + ioException.getMessage());
                ioException.printStackTrace();
            }

        }
        // Catch serialization error from dashboard.toJSON()
        catch (JsonProcessingException jsonException) {
            System.err.println("Error serializing the dashboard to JSON: " + jsonException.getMessage());
            jsonException.printStackTrace();
        }
    }
}