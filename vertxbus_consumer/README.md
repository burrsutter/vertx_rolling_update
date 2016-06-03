Running local

cd webroot

npm install

cd ..

mvn clean compile package

java -jar target/vertxbusconsumer-1.0.0-fat.jar -cluster -cp .

Running on OpenShift/CDK

oc login 10.1.2.2:8443 -u openshift-dev -p devel 

oc new-project vertxbus

mvn clean compile package -Popenshift

oc new-build --binary --name=consumer -l app=consumer

oc start-build consumer --from-dir=. --follow

oc new-app consumer -l app=consumer

oc delete service consumer
oc expose dc consumer --port=8080
oc expose service consumer


