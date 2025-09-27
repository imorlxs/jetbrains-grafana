package internship;

import com.grafana.foundation.dashboard.DataSourceRef;

public record DataSourceConfig(String type, String uid) {
    public DataSourceRef toRef() {
        return new DataSourceRef(type, uid);
    }
}