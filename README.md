# CPU Usage Demo panel in Grafana using Prometheus and Foundation SDK.
This repository is meant for completing a task as part of the selection process at JetBrains. Using Foundation SDK, Java and GitHub actions, it generates a JSON containing a simple Grafana dashboard. It also uses the SDK to push that dashboard into my Grafana Cloud instance (with no data though) 
## Run the demo environment

This repository includes a [Docker Compose setup](./docker-compose.yaml) that runs Grafana, Prometheus, Prometheus Alertmanager, Loki, and an SMTP server for testing email notifications.

To run the demo environment:

```bash
docker compose up
```

You can then access:
- Grafana: [http://localhost:3000](http://localhost:3000/)
- Prometheus web UI: [http://localhost:9090](http://localhost:9090/)
- Alertmanager web UI: [http://localhost:9093](http://localhost:9093/)

### Generating test data

This demo uses [Grafana k6](https://grafana.com/docs/k6) to generate test data for Prometheus and Loki.

To run k6 tests and store logs in Loki and time series data in Prometheus, you'll need a k6 version with the `xk6-client-prometheus-remote` and `xk6-loki` extensions.

You can build the k6 version using the [`xk6` instructions](https://grafana.com/docs/k6/latest/extensions/build-k6-binary-using-go/) or Docker as follows:

<details>
  <summary>macOS</summary>

  ```bash
  docker run --rm -it -e GOOS=darwin -u "$(id -u):$(id -g)" -v "${PWD}:/xk6" \
    grafana/xk6 build v0.55.0 \
    --with github.com/grafana/xk6-client-prometheus-remote@v0.3.2 \
    --with github.com/grafana/xk6-loki@v1.0.0
  ```
</details>

<details>
  <summary>Linux</summary>

  ```bash
  docker run --rm -it -u "$(id -u):$(id -g)" -v "${PWD}:/xk6" \
    grafana/xk6 build v0.55.0 \
    --with github.com/grafana/xk6-client-prometheus-remote@v0.3.2 \
    --with github.com/grafana/xk6-loki@v1.0.0
  ```
</details>

<details>
  <summary>Windows</summary>

  ```bash
docker run --rm -it -e GOOS=windows -u "$(id -u):$(id -g)" -v "${PWD}:/xk6" `
  grafana/xk6 build v0.55.0 --output k6.exe `
  --with github.com/grafana/xk6-client-prometheus-remote@v0.3.2 `
  --with github.com/grafana/xk6-loki@v1.0.0
```

</details>


Once you've built the necessary k6 version, you can pre-populate data by running the [scripts from the `testdata` folder](./testdata/) as follows:

```bash
./k6 run testdata/<FILE>.js
```

The `testdata` scripts inject Prometheus metric and Loki log data, which can be used to define alert queries and conditions. You can then modify and run the scripts to test the alerts.


### Generating and downloading the JSON.
To generate the JSON you have to options:
  1. Run the Java Main included in the repository. It will generate a `cpu-usage-generated.json` file, that you can directly import to Grafana.
  2. Run the GitHub action `gradle.yml`. It will compile the proyect, run it, generate the JSON and uploading it as an artifact. You can download it going to the specific run of that action.
  3. Using `deploy-dashboard.yml`, and setting the secret `GRAFANA_TOKEN` and the variables `GRAFANACTL_VERSION`, `GRAFANA_SERVER` and `GRAFANA_STACK_ID`, you can use the observability-as-code approach and upload it directly using `grafanactl`
