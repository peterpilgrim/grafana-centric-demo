import java.time.LocalDate
import java.util.*;

println("""
/* 
=====================================================================================
    GENERATOR    GENERATOR    GENERATOR    GENERATOR     GENERATOR     GENERATOR    
=====================================================================================
*/

use centric;

""")

def teams=100..138

def endTime = LocalDate.of(2023, 10, 16)

println("""
delete from change_failure_rate;
delete from change_lead_rate;
delete from delivery;
delete from mean_time_to_recovery;
delete from perspectives;
""")

for (team in teams) {
    println """
    | insert into delivery( team_id, delivery_lead_time, rec_date ) values
""".stripMargin().stripTrailing()

    def recTime = LocalDate.of(2019, 1, 1)
    int delivery = 10 + Math.random() * 10;
    while ( recTime.isBefore(endTime)) {
        if ( Math.random() > 0.5 ) {
            delivery = delivery + (1 + (int)(Math.random()*20))
        }
        else {
            delivery = delivery - (1 + (int)(Math.random()*20))
            if ( delivery < 5)
                delivery = 5
        }
        recTime = recTime.plusDays( 3 + (int)(Math.random() * 21 ))
        print( "    ( $team, $delivery, '$recTime' )")
        if ( recTime.isBefore(endTime))
            print(",\n")
    }
    println(";")
}


for (team in teams) {
    println """
    | insert into change_lead_rate( team_id, change_lead_rate, rec_date ) values
""".stripMargin().stripTrailing()

    def recTime = LocalDate.of(2019, 1, 1)
    int changeLeadRate = 1000 + Math.random() * 100;
    while ( recTime.isBefore(endTime)) {
        if ( Math.random() > 0.5 ) {
            changeLeadRate = changeLeadRate + (1 + (int)(Math.random()*40))
        }
        else {
            changeLeadRate = changeLeadRate - (1 + (int)(Math.random()*20))
            if ( changeLeadRate < 5)
                changeLeadRate = 5
        }
        recTime = recTime.plusDays( 3 + (int)(Math.random() * 60 ))
        print( "    ( $team, $changeLeadRate, '$recTime' )")
        if ( recTime.isBefore(endTime))
            print(",\n")
    }
    println(";")
}


for (team in teams) {
    println """
    | insert into mean_time_to_recovery( team_id, mttr, rec_date ) values
""".stripMargin().stripTrailing()

    def recTime = LocalDate.of(2018, 1, 1)
    int meanTimeToRecovery = 400 + Math.random() * 100;
    while ( recTime.isBefore(endTime)) {
        if ( Math.random() > 0.5 ) {
            meanTimeToRecovery = meanTimeToRecovery + (1 + (int)(Math.random()*30))
        }
        else {
            meanTimeToRecovery = meanTimeToRecovery - (1 + (int)(Math.random()*30))
            if ( meanTimeToRecovery < 5)
                meanTimeToRecovery = 5
        }
        recTime = recTime.plusDays( 3 + (int)(Math.random() * 67 ))
        print( "    ( $team, $meanTimeToRecovery, '$recTime' )")
        if ( recTime.isBefore(endTime))
            print(",\n")
    }
    println(";")
}


for (team in teams) {
    println """
    | insert into change_failure_rate( team_id, change_failure, rec_date ) values
""".stripMargin().stripTrailing()

    def recTime = LocalDate.of(2019, 1, 1)
    int changeFailureRate = 10 + Math.random() * 80;
    while ( recTime.isBefore(endTime)) {
        if ( Math.random() > 0.5 ) {
            changeFailureRate = changeFailureRate + (1 + (int)(Math.random()*5))
            if ( changeFailureRate > 100 )
                changeFailureRate = 100
        }
        else {
            changeFailureRate = changeFailureRate - (1 + (int)(Math.random()*5))
            if ( changeFailureRate < 1)
                changeFailureRate = 1
        }
        recTime = recTime.plusDays( 3 + (int)(Math.random() * 81 ))
        print( "    ( $team, $changeFailureRate, '$recTime' )")
        if ( recTime.isBefore(endTime))
            print(",\n")
    }
    println(";")
}


for (team in teams) {
    println """
    | insert into perspectives( 
    |    team_id, repos_without_renovate, code_smells_per_repo,
    |    coverage, complexity, deprecated_helm_charts, 
    |    rec_date) values
""".stripMargin().stripTrailing()

    def recTime = LocalDate.of(2019, 1, 8)
    int cadence = 7
    int rnd = Math.random()
    if ( rnd < 0.4 ) {
        cadence = 7
    }
    else if ( rnd > 0.8 ) {
        cadence = 14
    }
    else {
        cadence = 10;
    }

    int reposWithoutRenovate = 7 + Math.random() * 16;
    int codeSmellsPerRepository = 50 + Math.random() * 250;
    double coverage = 45.0 + Math.random() * 45.0;
    double complexity = 50 + Math.random() * 45.0;
    int deprecatedHelmCharts = 10 + Math.random() * 50;

    while ( recTime.isBefore(endTime)) {
        if ( Math.random() < 0.333) {
            if (Math.random() > 0.5 ) {
                reposWithoutRenovate += (1 + Math.random() * 4 )
                if ( reposWithoutRenovate > 23)
                    reposWithoutRenovate = 23
            }
            else {
                reposWithoutRenovate -= (1 + Math.random() * 4 )
                if ( reposWithoutRenovate < 0)
                    reposWithoutRenovate = 0
            }
        }
        if ( Math.random() < 0.271) {
            if (Math.random() > 0.5 ) {
                codeSmellsPerRepository += + (1 + Math.random() * 15 )
                if ( codeSmellsPerRepository > 300)
                    codeSmellsPerRepository = 300
            }
            else {
                codeSmellsPerRepository -= (1 + Math.random() * 15 )
                if ( codeSmellsPerRepository < 0)
                    codeSmellsPerRepository = 0
            }
        }
        if ( Math.random() < 0.825) {
            if (Math.random() > 0.5 ) {
                coverage += (1 + Math.random() * 7.0)
                if ( coverage > 100.0)
                    coverage = 100.0
            }
            else {
                coverage -= (1 + Math.random() * 8.0 )
                if ( coverage < 0.0)
                    coverage = 0.0
            }
        }
        if ( Math.random() < 0.911) {
            if (Math.random() > 0.5 ) {
                complexity += (1 + Math.random() * 5.0)
                if ( complexity > 100.0)
                    complexity = 100.0
            }
            else {
                complexity -= (1 + Math.random() * 6.0 )
                if ( complexity < 0.0)
                    complexity = 0.0
            }
        }
        if ( Math.random() < 0.75) {
            if (Math.random() > 0.5 ) {
                deprecatedHelmCharts += (1 + Math.random() * 4)
                if ( deprecatedHelmCharts > 60)
                    deprecatedHelmCharts = 60
            }
            else {
                deprecatedHelmCharts -= (1 + Math.random() * 4 )
                if ( deprecatedHelmCharts < 0)
                    deprecatedHelmCharts = 0
            }
        }

        recTime = recTime.plusDays( cadence )
        printf( "    ( %d, %d, %d, %.3f, %.3f, %d, '%s' )",
                team, reposWithoutRenovate, codeSmellsPerRepository, coverage, complexity, deprecatedHelmCharts, recTime )
        if ( recTime.isBefore(endTime))
            print(",\n")
    }
    println(";")
}