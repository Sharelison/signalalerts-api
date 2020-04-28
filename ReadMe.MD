# Signal Alerts API
This API provides a way to send messages to signal through a REST api. It is initially designed to forward Prometheus AlertManager alerts to Signal. 

## Getting Started

### Prerequisites
  - [signal-cli](https://github.com/AsamK/signal-cli)
#### To run on local machine:

      mvn spring-boot:run -Dspring-boot.run.profiles=local
NOTE: [signal-cli](https://github.com/AsamK/signal-cli) must be installed on the machine.

#### To run on Openshift/Kubernetes
This project is initially created to send alertmanager (prometheus) alerts to signal.
In the provided template (openshift/signalalerts-api.yml), a init container is defined that will install the signal cli on a persistent volume. 
This persistent volume is then mounted under the container containing this application making it possible to only having to register a sender (phone) number once. 
Once a number is registered, all the signal data that comes with this, is saved on the persistent volume. Which means the application can now be restarted and destroyed without losing the
signal data. New numbers can also be registered. This will also automatically be saved on the persistent volume. This makes the application stateless and container ready. This makes it also possible to run multiple instances (replicas) of the application. 

For testing purposes, there's also a ephemeral template (openshift/signalalertsapi-ephemeral) that doesn't create or use a persistent volume but a emptydir to install signal. 
Keep in mind that signal has a limit per day of registering users/phone numbers. With this template it will not be able to restart or scale your application because every new replica should register a new phonenumber.
This is why it's highly recommended to use the other template with persistent volumes.

#### Usage
###### NOTE: For the ephemeral template the same steps should be applied, just replace signalalerts-api.yml by signalalertsapi-ephemeral.yml.

First time installing the application:

    oc import-image openjdk/openjdk-11-rhel7 --from=registry.access.redhat.com/openjdk/openjdk-11-rhel7 --confirm

    oc process -f openshift/signalalerts-api.yml | oc apply -f -

This will create all the resources needed to run this project on a openshift cluster. (For kubernetes, the image must be built first)
The builds rely on the java image that we import with the first step (oc import-image).

In de deploymentconfig created by the template make sure to update the environment variables SENDER_PHONENUMBER and RECIPIENT_PHONENUMBER.
To make this easier, these values can also be passed as a parameter in the template:

    oc process -f openshift/signalalerts-api.yml -p SENDER_PHONENUMBER=<yourphonenumber> -p RECIPIENT_PHONENUMBER=<recipientphonenumber> | oc apply -f -

Once the application is installed and runnning on the cluster, register and verify your (phone) number. 

To register send a GET request to the route endpoint created by the template (oc get route signalalertsapi).

    http://signalalertsapi-<namespace>.<yourdomain>/register

After this, an sms is sent to the sender phone number (SENDER_PHONENUMBER) with the verificationcode.
To verify the number, send a GET request to:

    http://signalalertsapi-<namespace>.<yourdomain>/verify?code=<verificationcode>
NOTE: Replce verificationcode by the actual verification code.

Once this is done, the application is all set and can be used to send messages. 

To send a custom message send a POST request with a string in the body to:

    http://signalalertsapi-<namespace>.<yourdomain>/send

To forward messages from prometheus alertmanager to this application update the prometheus config with a receiver:

       receivers:
         - name: default-receiver
         - name: signalapp-receiver
           webhook_configs:
             - url: "http://signalalertsapi.<your-namespace>.svc:8080/send/prometheusalert"
In openshift this is usually a secret called alertmanager-main. (secret/alertmanager-main)

To learn more about prometheus configs, see: https://prometheus.io/docs/alerting/configuration/#receiverhere.

All set, this is everything that's needed to send alerts from a prometheus alertmanager running on openshift to Signal. 
