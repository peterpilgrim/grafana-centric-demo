# GRAFANA CENTRIC DEMO 

[MS05 Grafana Team centric dashboard page design](https://tools.hmcts.net/jira/browse/DTSSE-3660) 

We currently have dashboards for high and medium levels of detail but currently lack a low level view of teams performance.
We need to design and prototype the type of information we think we should be displaying and how.


## Grafana Login


Initalise logon to Grafana


http://localhost:3000/login

Username: admin
Password: admin


Change the admin to something else


## Requirements

The requirements

   * [Homebrew](https://brew.sh/)
   * [Groovy](https://groovy-lang.org/) V4.0 or better
   * [Docker](https://www.docker.com/products/docker-desktop/) V23 or better 
   * [MySQL Community Download](https://dev.mysql.com/downloads/mysql/) V8.1 or better

### Homebreww

Get Homebrew for Mac OS X

### Groovy

In order to install Groovy, you can download it from the official site [Groovy](https://groovy-lang.org/). 
You then unpack the distribution and install it in a location of your choice on your machine.

Or you can use something like [SDK Manager](https://sdkman.io/) and you can execute a command like this:

```zsh
sdk install groovy
```

See also https://sdkman.io/sdks#groovy for more information.


### Install Docker

We need a local installation of Docker database server.

Use [Homebrew](https://formulae.brew.sh/formula/docker) to install Docker on your MacBook Pro

```zsh
 brew install docker 
```

### Install MySQL 

We need a local installation of MySQL database server.
Use [Homebrew](https://brew.sh) to install MYSQL on your MacBook Pro

```zsh
 brew install mysql 
```

Configure MySQL to automatically start as a background whenever you restart your machine.

```zsh
brew services start mysql
```

Check the services 

```zsh
brew services list
```

You should be able to login to MySQL in a terminal window.

```zsh
mysql -u root
```

There is no password set up. For your own security, I recommend strongly setting a "root" password. 


## Create Centric Database

There is a DB schema creation script [Local Grafana Centric SQL](local-grafana-centric.sql)
Run the script into the MySQL

```zsh
cat local-grafana-centric.sql | mysql -u root
```

This script generates the DB schema called `centric` and the following tables:

   * change_failure_rate
   * change_lead_rate
   * combined_state
   * delivery
   * engineering
   * mean_time_to_recovery
   * perspectives

## Run Generator and Aggregation Scripts

We have two Groovy scripts that generate random data sets for the Grafana.

In a terminal, run the Groovy script `generator.groovy`


```zsh
groovy  generator.groovy
```

This script pumps the SQL insertion statements to the standard output. So run it again and pipe into the MySQL

```zsh
groovy  generator.groovy | mysql -u root
```

This script generates data for the four key metrics.

  1. Delivery (Hours)
  2. Change Failure Rate (%)
  3. Change Lead Rate (%)
  4. Mean Time To Recovery (Hours) 

Afterwards, run the Groovy script `combinator.groovy`


```zsh
groovy  -cp ~/lib/mysql-connector-j-8.1.0.jar  combinator.groovy 
```

Note: In order to execute this script, you need to also download the 
[MySQL Connector Java](https://dev.mysql.com/downloads/connector/j/), 
which is the JDBC driver for MySQL 8.1.0 or better.


The Combinator script creates the aggregated view in a DB table `combined_state`. 
It gets around the limitation that Grafana does not LOOP around template variables.

The script also generated the `perspectives` table data.

## Import the Grafana Dashboard

There are Grafana JSON files

  1. [Centric Dashboard](Centric-Dashboard.json)
  2. [Perspective Fixed Dashboard](Fixed-Dashboard-Perspective.json)


## Grafana Tutorial

This project borrows the official [Grafana Getting Started Tutorial](https://grafana.com/tutorials/grafana-fundamentals/).
Follow the instructions to  create your local Docker installation with Grafana, Prometheus, etc

You can find the Tutorial in the [sub directory](tutorial-environment)

Once up get Docker up and running, you should be able to see the processes something like this:

```zsh
docker ps
CONTAINER ID   IMAGE                      COMMAND                  CREATED       STATUS       PORTS                    NAMES
37c72f8bf0c2   grafana/promtail:2.0.0     "/usr/bin/promtail -…"   2 weeks ago   Up 2 weeks                            tutorial-environment-promtail-1
3a1758a7943f   prom/prometheus:v2.34.0    "/bin/prometheus --c…"   2 weeks ago   Up 2 weeks   0.0.0.0:9090->9090/tcp   tutorial-environment-prometheus-1
ee561a4bbda4   tutorial-environment-app   "/bin/sh -c '/app ht…"   2 weeks ago   Up 2 weeks   0.0.0.0:8081->80/tcp     tutorial-environment-app-1
d4601e9ed01c   grafana/grafana:10.0.0     "/run.sh"                2 weeks ago   Up 2 weeks   0.0.0.0:3000->3000/tcp   tutorial-environment-grafana-1
60af0c8a0570   grafana/tns-db:latest      "/db"                    2 weeks ago   Up 2 weeks   0.0.0.0:8082->80/tcp     tutorial-environment-db-1
888ab21c1f26   grafana/loki:2.4.2         "/usr/bin/loki -conf…"   2 weeks ago   Up 2 weeks   0.0.0.0:3100->3100/tcp   tutorial-environment-loki-1
```


## Other stuff

In order to configure Docker Grafana to see the MySQL database outside the server use the special domain "host.docker.internal"
So inside Docker, your MySQL connection is:

      hostname:  host.docker.internal
      username:  root
      password:  <>
      database:  centric



### Grafana Hints and Tips

Setting the absolute time range, configure the `To` to 'now' 
and in the `From` use an expression:

| Expression | Meaning             |
|------------|---------------------|
| `now-3d`   | the last three days |
| `now-7d`   | the last week       |
| `now-10d`  | the last 10 days    |
| `now-14d`  | the last fortnight  |
| `now-1M`   | the last month      |
| `now-2M`   | the last two months |
| `now-3M`   | the last 3 months   |
| `now-6M`   | the last 6 months   |
| `now-1y`   | the last year       |



Peter Pilgrim

October/November 2023
