# Performance Dashboard

A sample monitoring dashboard that can be used on external systems. This is a Maven project written in Java using Spring Boot, Thymeleaf, and Twitter Bootstrap APIs. For the UI itself, makes use of the theme StartBootstrap SB Admin 2.

Theme demo:
https://blackrockdigital.github.io/startbootstrap-sb-admin-2/pages/index.html

A basic security implementation exists to view the site.

Use the username/password:	admin/password

*Note:* This was a fun experiment at the time for teaching me how I could create dashboards by hand. However, if you are looking for an open-source solution to meet your real performance monitoring/alerting needs, I would highly recommend using a combination of Grafana and something like Prometheus + node_export for a fully featured solution. These can be easily deployed on any server via Docker for very quick implementation.

# Dashboard

## Metrics

The dashboard provides up to date system performance metrics.

* **System CPU Load Graph** - The percentage that the CPU has been utilized over the last minute.
* **Average CPU Load Graph** - The average system load over the past 1min-, 5min-, and 15min- intervals.
* **File System Utilization** - The percentage of used and free space on the filesystem.
* **System Memory Utilization** - The percentage of used and free system memory.
