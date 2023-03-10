# Getting Started

> Info: All steps was executed for linux OS, for windows you should modified the commands

## Download from github

We must download the project from gitlab with the following command

```shell
git clone https://github.com/lperilla/basf-challenge.git
```

Go to downloaded folder:

```shell
cd basf-challenge
```

## What we will Need

You will also need a Kubernetes cluster and the command line tool Kubectl. You can create a cluster locally by using
Minikube on Docker. Before proceeding further, verify that you can run kubectl commands from the shell. The following
example uses minikube:

```shell
$ kubectl cluster-info
Kubernetes control plane is running at https://192.168.49.2:8443
CoreDNS is running at https://192.168.49.2:8443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
```

You should also run the following command:

```shell
$ kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.43.0.1    <none>        443/TCP   7m13s
```

## Build Application

You can then build the application:

* Linux:

```Shell
./mvnw install
```

* Windows:

```cmd
./mvnw.cmd install
```

Then you can see the result of the build. If the build was successful, you should see a JAR file similar to the
following:

```shell
ls -l target/*.jar
-rw-rw-r-- 1 lperilla lperilla 23670647 ene 25 22:47 target/basf-challenge-0.0.1-SNAPSHOT.jar
```

### Containerize the Application

For containerizing the application, as long as you are already building a Spring Boot jar file, you only need to call
the plugin directly.
The following command uses Maven:

* Linux:

```shell
./mvnw spring-boot:build-image
```

* Windows:

```
./mvnw.cmd spring-boot:build-image
```

You can run the container locally:

```shell
$ docker run -p 8080:8080 basf-challenge:${project.ersion}
```

Then you can check that it works in another terminal:

* Linux:

```shell
$ curl localhost:8080/basf-challenge/actuator/health
```

You cannot push the image unless you authenticate with Dockerhub (docker login), but there is already an image there
that should work. If you were authenticated, you could:

```shell
docker tag ${IMAGE_ID} lperilla/challenges:basf-challenge
docker push lperilla/challenges:basf-challenge
```

## Pull an Image from a Private Registry

See the follow link:

https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/

## Minikube

```shell
minikube start
```

## Deploy the Application to Kubernetes

Now you have a container that runs and exposes port 8080, so all you need to make Kubernetes run the deployment.yaml

```shell
kubectl apply -f k8s/development/
```

Check that the application is running:

```
NAME                                  READY   STATUS      RESTARTS   AGE
pod/basf-challenge-7c7cff44f5-kh58j   1/1     Running     0          9h

NAME                             TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)    AGE
service/basf-challenge-service   ClusterIP   10.98.19.84   <none>        8080/TCP   9h
service/kubernetes               ClusterIP   10.96.0.1     <none>        443/TCP    11h

NAME                             READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/basf-challenge   1/1     1            1           9h

NAME                                        DESIRED   CURRENT   READY   AGE
replicaset.apps/basf-challenge-7c7cff44f5   1         1         1       9h
```

Now you need to be able to connect to the application, which you have exposed as a Service in Kubernetes. One way to do
that, which works great at development time, is to create an SSH tunnel:

* port-forward mongodb:

```
kubectl port-forward mongodb-77fdf745dd-wcwvf 27017:27017
```

* port-forward application:

```
kubectl port-forward pod/basf-challenge-... 8080:8080
```

Then you can verify that the app is running in another terminal:

```shell
$ curl http://localhost:8080/basf-challenge/actuator/health
{"status":"UP"}
```

To delete all components from kubernetes, you can use the follow command:

```
kubectl delete -f k8s/development/
```

Get a shell to the running container:

```
kubectl exec --stdin --tty basf-challenge-... -- /bin/bash
```

To see the kubernetes dashboard, we need to start it with the follow command:

```shell
minikube dashboard
```

Now you should can to see the dashboard from a web browser:

http://127.0.0.1:40337/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/service?namespace=default

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/#build-image)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#actuator)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#web.reactive)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)