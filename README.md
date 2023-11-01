# GRAFANA CENTRIC DEMO 

## Install Docker

We need a local installation of Docker database server.

Use [Homebrew](https://formulae.brew.sh/formula/docker) to install Docker on your MacBook Pro

```zsh
 brew install docker 
```

## Install MySQL 

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

There is no password set up. For your security, you should set a "root" password. 


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
groovy  combinator.groovy
```

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


## Other stuff

In order to configure Docker Grafana to see the MySQL database outside the server use the special domain "host.docker.internal"
So inside Docker, your MySQL connection is:

   hostname:  host.docker.internal
   username:  root
   password:  <>
   database:  centric



Peter Pilgrim
October/November 2023
