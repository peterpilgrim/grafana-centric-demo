# THOUGHTS AND IDEAS

I wondering if I should convert the `generator.groovy` to actually insert the data directly into the database. 
In other word, the code invokes the Groovy JDBC API directly.

Ergo the script would run like the `combinator.groovy` and be dependent on the MySQL Java Connector.

```zsh
groovy  -cp ~/lib/mysql-connector-j-8.1.0.jar  generator.groovy 
```

