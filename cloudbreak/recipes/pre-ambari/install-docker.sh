#!/bin/bash

# Setup Docker CE Repo
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# Install Docker CE
sudo yum install -y docker-ce

# Keep containers alive during daemon downtime, enable debug logging
cat >> /etc/docker/daemon.json << EOF
{
  "live-restore": true,
  "debug": true
}
EOF

# Start Docker
sudo systemctl start docker
