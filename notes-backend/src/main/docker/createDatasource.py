AdminTask.setJVMProperties('[-nodeName DefaultNode01 -serverName server1 -verboseModeClass false -verboseModeGarbageCollection true -verboseModeJNI false -runHProf false -hprofArguments -debugMode true -debugArgs "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=7777" -executableJarFileName -genericJvmArguments "-Xnoloa" -disableJIT false]')

AdminTask.createAuthDataEntry('[-alias sa -user sa -password sa -description ]')
provider = AdminTask.createJDBCProvider('[-scope Cell=DefaultCell01 -databaseType User-defined -providerType "User-defined JDBC Provider" -implementationType User-defined -name H2 -description -classpath [${WAS_LIBS_DIR}/h2.jar ] -nativePath "" -implementationClassName org.h2.jdbcx.JdbcDataSource ]')
datasource = AdminTask.createDatasource(provider, '[-name h2 -jndiName jdbc/h2 -dataStoreHelperClassName com.ibm.websphere.rsadapter.GenericDataStoreHelper -containerManagedPersistence true -componentManagedAuthenticationAlias DefaultNode01/sa]')
#-configureResourceProperties [[url java.lang.String jdbc:h2:mem:testdatabase] [user java.lang.String sa] [password java.lang.String sa]]
#-configureResourceProperties [url java.lang.String jdbc:h2:mem:testdatabase;DB_CLOSE_DELAY=-1]

AdminConfig.create('MappingModule', datasource, '[[authDataAlias DefaultNode01/sa] [mappingConfigAlias ""]]')

AdminConfig.create('StringNameSpaceBinding', AdminConfig.getid('/Cell:DefaultCell01/'), '[[name "globalNameSpaceBinding"] [nameInNameSpace "globalNameSpaceBinding"] [stringToBind "DasIstEinAnderesHaus"]]')

AdminConfig.save();