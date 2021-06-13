FROM jenkins/jenkins:2.277.4-lts

ENV JAVA_OPTS=-Djenkins.install.runSetupWizard=false

USER root


RUN apt-get update && apt-get install -y jq ldap-utils ca-certificates \
    && update-ca-certificates

USER jenkins

COPY --chown=jenkins:jenkins ldap.hpi /usr/share/jenkins/ref/plugins/

COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
