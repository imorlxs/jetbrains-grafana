package internship;

import java.util.List;
import com.grafana.foundation.common.Constants;
import com.grafana.foundation.dashboard.*;
import com.grafana.foundation.prometheus.DataqueryBuilder;
import com.grafana.foundation.prometheus.QueryEditorMode;
import com.grafana.foundation.timeseries.PanelBuilder;

public class CpuDashboardGenerator {

    static final DataSourceConfig PROMETHEUS = new DataSourceConfig("prometheus", "DS_PROMETHEUS_UID");
    private static final String DASHBOARD_TITLE = "CPU Usage";
    private static final String DASHBOARD_UID = "cpu-usage-generated";
    private static final List<String> DASHBOARD_TAGS = List.of("generated", "cpu", "java");
    private static final String REFRESH_INTERVAL = "1m";
    private static final String TIME_FROM = "now-1m";
    private static final String TIME_TO = "now";
    private static final String DATASOURCE_TYPE = "prometheus";
    private static final String DATASOURCE_UID = "DS_PROMETHEUS_UID";

    private final DataSourceRef dataSourceRef;

    public CpuDashboardGenerator() {
        this.dataSourceRef = new DataSourceRef(DATASOURCE_TYPE, DATASOURCE_UID);
    }

    public Dashboard generateDashboard() {
        return new DashboardBuilder(DASHBOARD_TITLE)
                .uid(DASHBOARD_UID)
                .tags(DASHBOARD_TAGS)
                .refresh(REFRESH_INTERVAL)
                .time(new DashboardDashboardTimeBuilder()
                        .from(TIME_FROM)
                        .to(TIME_TO)
                )
                .timezone(Constants.TimeZoneBrowser)
                .withRow(new RowBuilder("Overview"))
                .withPanel(buildCpuPanel()) //
                .build();
    }

    // Private method to build the CPU Usage Panel
    private PanelBuilder buildCpuPanel() {
        return new PanelBuilder().
                title("CPU Usage").
                unit("%").
                min(0.0).
                withTarget(new DataqueryBuilder().
                        expr("cpu_usage").
                        datasource(PROMETHEUS.toRef()).
                        editorMode(QueryEditorMode.BUILDER).
                        legendFormat("__auto")
                );
    }
}