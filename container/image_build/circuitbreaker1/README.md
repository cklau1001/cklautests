[![Image build workflow](https://github.com/cklau1001/cklautests/actions/workflows/image-build-flow.yml/badge.svg)](https://github.com/cklau1001/cklautests/actions/workflows/image-build-flow.yml)
[![Pushed to Docker Hub](https://img.shields.io/badge/docker_hub-released-blue.svg?logo=docker)](https://github.com/cklau1001/cklautests/actions/workflows/image-build-flow.yml)

# Problem statement
Setting up a Spring cloud application consists of a number of steps, particularly if the automatic build and image push by CI/CD pipeline is needed.
This project serves to provide a simple example to achieve that. It consists of the following useful components.
- Fetch downstream information by Feign
- Enable circuit breaker by resilience4j
- Retrieve sensitive information from Kubenetes secret by Spring Kubernetes

The build process creates a docker image using a custom CNB buildpack created in [custom_builder](../../CNB/custom_builder) from a private repository using Github secrets. 
The process builds the image for all branches but only pushes a new image only for **main** branch. 
It can show how to achieve that in a github action.

```requirements
# build process
Github action --> maven spring-boot:build-image --- push --> Docker hub (if branch == 'main')
```
It also contains the files to deploy to target kubernetes by either Kustomize or Helm.

# Notes
- When using Spring Kubernetes, a kubernete profile is set up that can be used for those Kube-specific settings.
- This application retrieves the Kubernetes secrets via a mountPath, that has to be specific as shown in application.yml before
Spring Kubernetes can access the secrets.
```yaml
  config:
  #  import: optional:configtree:/etc/secret/    # If not using Spring Kubernetes
    import: "optional:kubernetes:"

```
- Spring Kubernetes tries to access configMap and secret using the **spring.application.name**, it may not be
  flexible.
- RBAC may be used to grant get, list, watch on secrets and configmap for Spring Kubernetes.
- Before deploying the image by Helm, please ensure that no resources have been deployed by a means other than that because Helm
  expects to manage all of its deployed resources. 


