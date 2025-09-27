package internship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.grafana.foundation.dashboard.Dashboard;

class DashboardWrapper {
    @JsonProperty("apiVersion")
    private String apiVersion;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("metadata")
    private Metadata metadata;

    @JsonProperty("spec")
    private Dashboard spec;

    public DashboardWrapper(String apiVersion, String kind, Metadata metadata, Dashboard spec) {
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.spec = spec;
    }
}

class Metadata {
    @JsonProperty("name")
    private String name;

    public Metadata(String name) {
        this.name = name;
    }
}