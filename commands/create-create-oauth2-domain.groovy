import grails.util.*

description( "Creates a CreateOauth2Domain" ) {
    usage "grails create-create-oauth2-domain [package]"
    argument name:'Package Name', description:"Package where to create the domain", required:true
    flag name:'force', description:"Whether to overwrite existing files"
}

def model = model(args[0])
boolean overwrite = flag('force')
render template: template('create-oauth2-domain/PersonDomain.groovy'),
       destination: file("grails-app/domain/${model.packagePath}/Person.groovy"),
       model: model,
       overwrite: false
render template: template('create-oauth2-domain/SecurityRoleDomain.groovy'),
       destination: file("grails-app/domain/${model.packagePath}/SecurityRole.groovy"),
       model: model,
       overwrite: false
 render template: template('create-oauth2-domain/PersonSecurityRoleDomain.groovy'),
       destination: file("grails-app/domain/${model.packagePath}/PersonSecurityRole.groovy"),
       model: model,
       overwrite: false
