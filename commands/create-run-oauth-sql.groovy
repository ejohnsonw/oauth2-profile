import grails.util.*

description( "Creates a RunOauthSql" ) {
    usage "grails create-run-oauth-sql [NAME]"
    argument name:'Database Name', description:"The name of the RunOauthSql to create", required:true
    flag name:'force', description:"Whether to overwrite existing files"
}

def model = model(args[0])
boolean overwrite = flag('force')

render template: template('run-oauth-sql/RunOauthSql.groovy'),
       destination: file("grails-app/sql/${model.packagePath}/${model.simpleName}.groovy"),
       model: model,
       overwrite: overwrite

def db = [
    moduleGroup: 'mysql',
    moduleName: 'mysql-connector-java',
    moduleVersion: '5.1.18',
    driver: "com.mysql.jdbc.Driver",
    url: 'jdbc:mysql://localhost:3306/bham',
    user: mySqlUser,
    password: mySqlPassword
]

configurations {
    sql
}    


task connect << {

        // This is needed to get mySql driver onto the Groovy/Gradle classpath 
        configurations.sql.each { file ->
          println "Adding URL: $file"
          gradle.class.classLoader.addURL(file.toURI().toURL())
        }

        def sql = groovy.sql.Sql.newInstance(db.url, db.user, db.password, db.driver)

        sql.execute("actStatusCodeLkp.sql") 
        String sqlFilePath = "src/main/resources/sqlscripts/actStatusCodeLkp.sql"
        String sqlString = new File(sqlFilePath).text
        sql.execute(sqlString)

        sql.close()

     }