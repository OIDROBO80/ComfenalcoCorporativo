# docker build -t smartfit.admin .
# docker run -p 4000:80 smartfit.admin

FROM jboss/wildfly
ADD admin.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]