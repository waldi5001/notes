FROM ibmcom/websphere-traditional:latest

#RUN chown was:root /work/app/notes-backend.war

COPY --chown=was:root *.py work/config/
COPY --chown=was:root h2.jar /opt/IBM/WebSphere/AppServer/lib/h2.jar

RUN /work/configure.sh