FROM registry.access.redhat.com/openjdk/openjdk-11-rhel7

LABEL maintainer="Sharelison <sharelison@hotmail.com>"

USER root

ENV VERSION=0.6.7

RUN curl -o /home/signal-cli-"${VERSION}".tar.gz https://github.com/AsamK/signal-cli/releases/download/v"${VERSION}"/signal-cli-"${VERSION}".tar.gz

RUN tar xf signal-cli-"${VERSION}".tar.gz -C /opt && ln -sf /opt/signal-cli-"${VERSION}"/bin/signal-cli /usr/local/bin/

RUN rm /home/signal-cli-"${VERSION}".tar.gz

USER 1001