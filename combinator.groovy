import java.sql.*;
import groovy.sql.Sql

class Combinator {
    static void main(String[] args) {
        // Creating a connection to the database
        def sql = Sql.newInstance('jdbc:mysql://localhost:3306/centric',
                'root', '', 'com.mysql.jdbc.Driver')

        // Executing the query SELECT VERSION which gets the version of the database
        // Also using the eachROW method to fetch the result from the database

        sql.eachRow('SELECT VERSION()'){ row ->
            println row[0]
        }

        sql.connection.autoCommit = false

        def deleteSQL = "DELETE FROM combined_state"

        try {
            sql.execute(deleteSQL);
            sql.commit()
            println("Successfully committed ($deleteSQL")
        } catch(Exception ex) {
            sql.rollback()
            println("Transaction rollback ($deleteSQL)")
        }

        sql.eachRow('select * from engineering'){ team ->
            // *DEBUG* println( [team.ID, team.NAME])

            sql.connection.autoCommit = true

            int deliveryLeadTime = 0

            sql.eachRow("""
                select e.name, d.rec_date, d.delivery_lead_time
                    from delivery d, engineering e
                    where d.team_id = e.id and e.id=${team.ID}
                    order by rec_date desc limit 1
                    """) {row ->
                // *DEBUG* println( row[0] )
                // *DEBUG* println( "${row.name}, ${row.rec_date}, ${row.delivery_lead_time}" )
                deliveryLeadTime = row.delivery_lead_time
            }

            int changeFailureRate = 0

            sql.eachRow("""
                select e.name, cfr.rec_date, cfr.change_failure
                    from change_failure_rate cfr, engineering e
                    where cfr.team_id = e.id and e.id=${team.ID}
                    order by rec_date desc limit 1
                    """) {row ->
                changeFailureRate = row.change_failure
            }

            int changeLeadRate = 0

            sql.eachRow("""
                    select e.name, clr.rec_date, clr.change_lead_rate
                        from change_lead_rate clr, engineering e
                        where clr.team_id = e.id and e.id=${team.ID}
                        order by rec_date desc limit 1;
                    """) {row ->
                changeLeadRate = row.change_lead_rate
            }

            int meanTimeToRecovery = 0

            sql.eachRow("""
                        select e.name, m.rec_date, m.mttr
                        from mean_time_to_recovery m, engineering e
                        where m.team_id = e.id and e.id=${team.ID}
                        order by rec_date desc limit 1;
                    """) {row ->
                meanTimeToRecovery = row.mttr
            }


            printf( "| %36s | %4d | %4d | %4d | %4d |\n",  team.NAME,  deliveryLeadTime, changeFailureRate, changeLeadRate, meanTimeToRecovery )

            sql.connection.autoCommit = false

            def insertSQL = """
                INSERT INTO  combined_state (
                team_id, 
                delivery_lead_time,
                change_lead_rate,
                change_failure,
                mttr) 
                VALUES ( ${team.ID}, ${deliveryLeadTime}, ${changeLeadRate}, ${changeFailureRate}, ${meanTimeToRecovery} )
                """

            try {
                sql.execute(insertSQL);
                printf("Successfully committed (engineering team='%s'\n)", team.NAME)
            } catch(Exception ex) {
                sql.rollback()
                println("Transaction rollback (engineering team='%s'\n)", team.NAME)
            }
        }

        // Always close the connection to DB
        sql.close()
    }
}
