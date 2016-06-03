Running Local

mvn clean compile package

java -jar target/vertxbuspublisher-1.0.0-fat.jar -cluster -cp .

Running on OpenShift/CDK

oc login 10.1.2.2:8443 -u openshift-dev -p devel 

oc new-project vertxbus

mvn clean compile package -Popenshift

oc new-build --binary --name=vertxpublisher -l app=vertxpublisher

oc start-build vertxpublisher --from-dir=. --follow

oc new-app vertxpublisher -l app=vertxpublisher

oc patch dc/vertxpublisher -p '{"spec":{"template":{"spec":{"containers":[{"name":"vertxpublisher","readinessProbe":{"httpGet":{"path":"/api/health","port":8080}}}]}}}}'





