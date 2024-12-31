End-to-End Bank Application Deployment using DevSecOps on AWS EKS
This is a multi-tier bank an application written in Java (Springboot).
Login diagram Transactions diagram# CI/CD Pipeline with AWS EKS and Jenkins

This repository provides a complete guide to setting up a CI/CD pipeline using Jenkins, Docker, AWS EKS, and other DevOps tools. The pipeline includes code quality checks, containerization, deployment, and monitoring.

## Tech Stack

- **Code Management:** GitHub
- **Containerization:** Docker
- **Continuous Integration (CI):** Jenkins
- **Security Checks:** OWASP, Trivy
- **Code Quality:** SonarQube
- **Continuous Deployment (CD):** ArgoCD
- **Caching:** Redis
- **Container Orchestration:** AWS EKS (Kubernetes)
- **Monitoring:** Helm, Grafana, Prometheus
- **Infrastructure as Code (IaC):** Terraform

---

## Pipeline Overview

1. **CI Pipeline:**
   - Builds the code.
   - Pushes Docker images to a repository.

2. **CD Pipeline:**
   - Updates the application version.
   - Deploys the application on AWS EKS using ArgoCD.

---

## Prerequisites

- **AWS Region:** North California (us-west-1)
- **AWS EC2 Instances:**
  - **Master Node:**
    - 2 CPUs, 8 GB RAM, 29 GB storage (t2.large)
    - Docker and Jenkins installed.
  - **Worker Node:**
    - 2 CPUs, 8 GB RAM, 29 GB storage (t2.large)
    - Docker and Java installed.

- **EKS Cluster:**
  - Created using `eksctl`.
  - IAM OIDC Provider associated.

---

## Environment Setup

### 1. Docker Installation
```bash
sudo apt-get update
sudo apt-get install docker.io -y
sudo usermod -aG docker ubuntu && newgrp docker
```

### 2. Jenkins Installation
```bash
sudo apt update -y
sudo apt install fontconfig openjdk-17-jre -y
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update -y
sudo apt-get install jenkins -y
```

### 3. AWS CLI, kubectl, and eksctl Installation
```bash
# AWS CLI
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
sudo apt install unzip
unzip awscliv2.zip
sudo ./aws/install

# kubectl
curl -o kubectl https://amazon-eks.s3.us-west-2.amazonaws.com/1.19.6/2021-01-05/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin

# eksctl
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

---

## Pipeline Setup

### 1. EKS Cluster Creation
```bash
eksctl create cluster --name=wanderlust --region=us-west-1 --version=1.30 --without-nodegroup
eksctl create nodegroup --cluster=wanderlust --region=us-west-1 --name=wanderlust --node-type=t2.large --nodes=2
```

### 2. ArgoCD Installation
```bash
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
sudo curl --silent --location -o /usr/local/bin/argocd https://github.com/argoproj/argo-cd/releases/latest/download/argocd-linux-amd64
sudo chmod +x /usr/local/bin/argocd
```

### 3. Monitoring Setup
#### Prometheus & Grafana Installation
```bash
# Install Helm
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh

# Install Prometheus & Grafana
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
kubectl create namespace prometheus
helm install stable prometheus-community/kube-prometheus-stack -n prometheus
```

---

## Cleanup
To remove the cluster:
```bash
eksctl delete cluster --name=wanderlust --region=us-west-1
```

---

## Author

**Bismaya Parida**  
[LinkedIn](https://linkedin.com/in/bismaya-parida) | [GitHub](https://github.com/Bismaya2401)

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.
