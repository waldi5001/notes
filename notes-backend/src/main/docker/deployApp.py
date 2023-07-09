if AdminApplication.checkIfAppExists("query") == "true":
	AdminApp.uninstall("query")
	
if AdminApplication.checkIfAppExists("notes-backend") == "true":
	AdminApp.uninstall("notes-backend")

AdminApp.install('/work/app/notes-backend.war', '[ -nopreCompileJSPs -distributeApp -nouseMetaDataFromBinary -appname notes-backend -createMBeansForResources -noreloadEnabled -nodeployws -validateinstall warn -noprocessEmbeddedConfig -filepermission .*\.dll=755#.*\.so=755#.*\.a=755#.*\.sl=755 -noallowDispatchRemoteInclude -noallowServiceRemoteInclude -asyncRequestDispatchType DISABLED -nouseAutoLink -noenableClientModule -clientMode isolated -novalidateSchema -contextroot / -MapModulesToServers [[ notes-backend.war notes-backend.war,WEB-INF/web.xml WebSphere:cell=DefaultCell01,node=DefaultNode01,server=server1 ]] -MapWebModToVH [[ notes-backend.war notes-backend.war,WEB-INF/web.xml default_host ]]]' )

#AdminControl.completeObjectName('type=Application,name=notes-backend,*') 
#appManager = AdminControl.queryNames('cell=DefaultCell01,node=DefaultNode01,type=ApplicationManager,process=server1,*')
#AdminControl.invoke(appManager, 'startApplication', "notes-backend")

AdminConfig.save()